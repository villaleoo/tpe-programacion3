package tpe.services;

import tpe.controllers.TaskController;
import tpe.structures.Task;
import tpe.utils.CSVReader;

import java.util.ArrayList;
import java.util.List;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Services {
    private final TaskController taskController;

    /*
     * Complejidad temporal: O(Max(n2, p)) siendo n las tareas del dataset, y p la
     * cantidad de procesadores del dataset.
     * Esto se debe a que en el método readTasks se está recorriendo línea a línea el dataset
     * y se está agregando las tareas a las distintas estructuras utilizadas
     */
    public Services(String pathProcesadores, String pathTareas) {
        CSVReader reader = new CSVReader();
        reader.readProcessors(pathProcesadores);
        reader.readTasks(pathTareas);
        this.taskController = new TaskController();


    }

    /*
     * Complejidad temporal: O(log2 n) siendo n la cantidad de claves existentes en el hashmap.
     * En este caso las claves representan a los ids de las tareas.
     */
    public Task service1(String id) {
        Task task = this.taskController.findTaskById(id);

        if (task == null) {
            System.out.println("No se encontraron tareas con id = " + id);
        }
        return task;
    }

    /*
     * Complejidad temporal: O(1). En este caso, el costo computacional de obtener la
     * lista de tareas críticas es constante.
     * Esto se debe a que simplemente implica acceder directamente a la
     * lista de tareas críticas o no críticas, sin necesidad de
     * realizar operaciones adicionales.
     */
    public List<Task> servicio2(boolean esCritica) {
        if (esCritica) return this.taskController.findCriticTasks();
        return this.taskController.findNotCriticTasks();
    }


    /*
     * Expresar la complejidad temporal del servicio 3.
     * La complejidad temporal es Log (n). En el peor de los casos accedera a todo el arbol, cuando se ingresen valores que incluyen todas las tareas
     */
    public List<Task> servicio3(int prioridadInferior, int prioridadSuperior) {
        ArrayList<Task> tasks = this.taskController.findByPriorities(prioridadInferior, prioridadSuperior);

        if (tasks == null) {
            System.out.println("No se encontraron tareas con niveles de prioridad entre " + prioridadInferior + " y " + prioridadSuperior + ".");
        }
        return tasks;
    }


}
