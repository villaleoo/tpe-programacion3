package tpe.services;

import tpe.structures.Processor;
import tpe.structures.Task;
import tpe.utils.CSVReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Backtracking {
    HashMap<String, ArrayList<Task>> taskAssignments = new HashMap<>();
    Float minTime = Float.MAX_VALUE;

    ArrayList<Task> tasks;
    ArrayList<Processor> processors;

    int totalSteps = 0;

    public Backtracking(String pathProcessor, String pathTasks) {
        CSVReader reader = new CSVReader();
        reader.readProcessors(pathProcessor);
        reader.readTasks(pathTasks);
        this.tasks = reader.getTasks();
        this.processors = reader.getProcessors();
    }

    public void getAssignments(float x) {
        HashMap<String, ArrayList<Task>> currentSol = new HashMap<>();
        this.initProcessors(currentSol);
        int currentTaskPos = 0;
        this.getAssignmentsBacktracking(currentSol, currentTaskPos, x);
        this.printSolution();
    }

    private void initProcessors(HashMap<String, ArrayList<Task>> currentSol) {
        for (Processor p : this.processors) {
            this.taskAssignments.put(p.getIdProc(), new ArrayList<>());
            currentSol.put(p.getIdProc(), new ArrayList<>());
        }
    }


    private void printSolution() {
        System.out.println("Solución obtenida");
        System.out.println("\n📄 Lista de asignacion:\n");
        for (String key : taskAssignments.keySet()) {
            ArrayList<Task> tasks = taskAssignments.get(key);
            System.out.println("Tareas asignadas al procesador " + key);
            System.out.println(tasks);
        }
        if (!this.findSolution()) {
            System.out.println("\t❌ Backtracking no encontró ninguna solución posible");
            this.minTime = null;
        }
        System.out.println("\t⌚ Tiempo total de ejecucion: " + this.minTime);


        System.out.println("\t📊 Cantidad de estados generados: " + this.totalSteps);
    }

    private boolean findSolution() {
        int totalTasks = this.tasks.size();
        int contTasks = 0;
        for (Processor p : processors) {
            ArrayList<Task> taskP = this.taskAssignments.get(p.getIdProc());
            contTasks += taskP.size();
        }

        return contTasks == totalTasks;
    }

    /**
     * Este algoritmo busca exhaustivamente todas las posibles combinaciones de
     * asignaciones de tareas a procesadores, y selecciona la que minimiza el tiempo
     * total de ejecución.
     * Para esto se utiliza un Hash para asignar las tareas a los procesadores
     * En cada paso de backtracking se evalúa si la solución encontrada minimiza el tiempo de
     * ejecución total de las tareas (isBestSolution).
     * Para la exploración de todas las posibles soluciones, se utiliza un for para recorrer
     * todos los procesadores en donde se pueda asignar la tarea actual. La asignación solo
     * es posible si cumple con las restricciones expresadas en el enunciado (canAssignTask)
     * @param actualSol      Solución actual en cada paso.
     * @param currentTaskPos Posición actual de la tarea a asignar
     * @param x              Parámetro enviado por el usuario para los procesadores no refrigerados
     */
    private void getAssignmentsBacktracking(HashMap<String, ArrayList<Task>> actualSol, int currentTaskPos, float x) {
        if (currentTaskPos == tasks.size()) {
            if (isBestSolution(actualSol)) {
                // Copia profunda de un hashmap, para evitar que siga teniendo referencias
                // al actualSol
                this.taskAssignments = this.deepCopy(actualSol);
                this.minTime = this.processorExecutionTime(this.taskAssignments);
            }
        } else {
            Task currentTask = tasks.get(currentTaskPos);
            for (Processor processor : processors) {
                if (this.canAssignTask(processor, currentTask, actualSol, x)) {
                    this.assignTask(actualSol, processor, currentTask);
                    currentTaskPos += 1;
                    totalSteps += 1;
                    getAssignmentsBacktracking(actualSol, currentTaskPos, x);
                    currentTaskPos -= 1;
                    this.unlinkTask(actualSol, processor, currentTask);
                }
            }
        }
    }

    private boolean isBestSolution(HashMap<String, ArrayList<Task>> currentSolution) {
        float currentExecutionTime = this.processorExecutionTime(currentSolution);
        return currentExecutionTime < this.minTime;
    }

    private HashMap<String, ArrayList<Task>> deepCopy(HashMap<String, ArrayList<Task>> currentSol) {
        return currentSol.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue()),
                        (oldValue, newValue) -> newValue,
                        HashMap::new));
    }

    /**
     * El tiempo de ejecución se define como el máximo tiempo de ejecución de un procesador.
     */
    private Float processorExecutionTime(HashMap<String, ArrayList<Task>> taskAssignments) {
        float maxTime = Float.MIN_VALUE;
        for (String key : taskAssignments.keySet()) {
            float executionTime = 0;
            ArrayList<Task> tasks = taskAssignments.get(key);
            executionTime += this.getExecutionTimeFromProcessor(tasks);
            if (executionTime > maxTime)
                maxTime = executionTime;
        }

        return maxTime;
    }

    private Float getExecutionTimeFromProcessor(ArrayList<Task> tasks) {
        float executionTime = 0;
        for (Task t : tasks) {
            executionTime += t.getTiempo_ejecucion();
        }

        return executionTime;
    }

    private boolean canAssignTask(Processor p, Task t, HashMap<String, ArrayList<Task>> currentSol, float x) {
        ArrayList<Task> tasksAssigned = currentSol.get(p.getIdProc());
        int countCriticTasks = (int) tasksAssigned.stream().filter(Task::isEsCritica).count();
        if (!p.isCooled()) {
            float executionTime = this.getExecutionTimeFromProcessor(tasksAssigned);
            if (executionTime + t.getTiempo_ejecucion() > x)
                return false;
        }
        return countCriticTasks < 2;
    }

    private void assignTask(HashMap<String, ArrayList<Task>> assignments, Processor p, Task t) {
        if (!assignments.containsKey(p.getIdProc())) {
            assignments.put(p.getIdProc(), new ArrayList<>());
        }
        ArrayList<Task> assignedTasks = assignments.get(p.getIdProc());
        assignedTasks.add(t);
    }

    private void unlinkTask(HashMap<String, ArrayList<Task>> assignments, Processor p, Task t) {
        if (assignments.containsKey(p.getIdProc())) {
            ArrayList<Task> assignedTasks = assignments.get(p.getIdProc());
            assignedTasks.remove(t);
        }
    }
}
