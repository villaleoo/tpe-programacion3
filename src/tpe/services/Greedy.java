package tpe.services;

import tpe.structures.Processor;
import tpe.structures.Task;
import tpe.utils.CSVReader;

import java.util.*;

//La estrategia greedy que se utilizo es a partir de 1 tarea. Apartar los procesadores que son aptos para esa tarea de la cola (cola ordenada por tiempo_ejecucion)
//una vez encontrados los procesadores aptos, asignarle la tarea al que no tiene tareas asignadas Ó al que al momento tiene el menor tiempo de ejecucion total.
//####################################################################################################################################################################

//Se creo la funcion getAssigment que seria la funcion de estructura general/clasica de greedy:
//se tiene un conjunto de solucion inicialmente vacio (solo con los procesadores cargados, sin tareas asignadas)
//se itera la cola de tareas (candidatos) hasta llegar a estar vacia. En esta iteracion se busca el procesador que minimiza el tiempo de ejecucion total para asignarle la primer tarea de la cola
//la cola de tareas va disminuyendo a medida que el algoritmo va encontrando procesadores adecuados para esa tarea (funcion getBetterProccesor)

//La funcion getBetterProccesor recibe una tarea (la primera de la cola) de getAssigment e itera los procesadores buscando los adecuados para asignarle esa tarea (funcion isFactible).
//para determinar si un procesador es adecuado para una tarea, se creo la funcion isFactible ---> de acuerdo a una tarea y un procesador, determina si
//el procesador cumple con los requisitos para asignarle esa tarea.
//getBetterProccesor a medida que va encontrando procesadores aptos, los va agregando a una lista de procesadores posibles
//cuando termina de obtener la lista de los procesadores posibles, se los envia a la funcion getProccShortExectTime para obtener, entre los posibles procesadores,
//el procesador que no tiene ninguna tarea asignada (seria el mejor para minimizar tiempos) ó el que menor tiempo de ejecucion tiene al momento de asignar la tarea.
//Una vez que se determina el mejor procesador para la primer tarea de la cola , getAssigment recibe el mejor procesador para
//la primer tarea en la cola, por lo que asigna la tarea al procesador en la estructura de solucion (HashMap solution) y elimina la tarea de la cola.
//se corta la iteracion de las tareas si se encuentra que una tarea no puede ser asignada a ningun procesador ó si se asignaron todas las tareas en cola.

//SE LLEGA A LA SOLUCION UNA VEZ QUE LA COLA DE TAREAS ESTA VACIA. SE ITERA CADA TAREA, BUSCANDO EL PROCESADOR ADECUADO QUE MINIMIZA EL TIEMPO DE EJECUCION.
//EL TIEMPO DE EJECUCION DE UN PROCESADOR ES LA SUMATORIA DEL TIEMPO DE EJECUCION DE LAS TAREAS QUE TIENE ASIGNADO.

//printAssigment -> llama a la funcion que asigna tareas getAssigment. Si se hace prueba con lista de tareas que excedan los requisitos muestra error.
//getAssigment -> itera cada tarea para asignarle el procesador que minimiza el tiempo de ejecucion total.
//getBetterProccesor -> itera cada procesador para encontrar el conjunto de procesadores que se les puede asignar la tarea.
//isFactible -> la utiliza getBetterProccesor para determinar cual procesador es apto para la tarea y cual no es apto.
//getProccShortExectTime -> la utiliza getBetterProccesor para determinar el procesador que minimiza el tiempo de ejecucion total. Trabaja con procesadores que son adecuados para 1 tarea



public class Greedy {
    private ArrayList<Processor> proccList;
    private Queue<Task> taskQueue;
    private HashMap<String, ArrayList<Task>> solution;
    private int qTask;

    public Greedy(String pathProcessor, String pathTasks){
        CSVReader reader= new CSVReader();
        ArrayList<Task> tasksList= reader.getTasks();
        reader.readProcessors(pathProcessor);
        reader.readTasks(pathTasks);

        this.proccList=new ArrayList<>(reader.getProcessors());
        this.solution=new HashMap<>();
        this.sortList(tasksList);
        this.taskQueue= new LinkedList<>(tasksList);
        this.qTask=this.taskQueue.size();

    }

    private void sortList (ArrayList<Task> tasks){
        //ORDENA LA LISTA DE TAREAS POR TIEMPO DE EJECUCION Y NIVEL DE PRIORIDAD -> ante iguales tiempo de ejecucion, prioritarias primero.
        //se ordeno las que mas tiempo de ejecucion conllevan primero para minimizar el tiempo de ejecucion final.
        Comparator<Task> compPriorityAndEjecTime = new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                int timeEjectComp = Integer.compare((int) t2.getTiempo_ejecucion(), (int) t1.getTiempo_ejecucion());
                if (timeEjectComp != 0) {
                    return timeEjectComp;
                }
                return Integer.compare(t2.getNivel_prioridad(), t1.getNivel_prioridad());

            }
        };

        tasks.sort(compPriorityAndEjecTime);
    }

    //funcion que imprime en pantalla la solucion del servicio
    public void printAssigment(Float x){
        this.getAssigment(x);
        ArrayList<String> dataProccBigTime= this.getDataProccBigExecTime();

        this.printEstateSolution();
        if(this.isOptimalSolution()){
            System.out.println("\n\t💻 Valor ingresado de X: "+x+";");
            System.out.println("\t📈 El procesador con mayor tiempo de ejecucion es: id_procesador = "+dataProccBigTime.getFirst()+";");
            System.out.println("\t⌚ Tiempo total de ejecucion: "+dataProccBigTime.getLast()+". (sumatoria de tiempo_ejecucion de las tareas en el "+dataProccBigTime.getFirst()+");");
            System.out.println("\t📄 Total de tareas en cola previo a comenzar la asignacion: "+this.qTask+";");
            System.out.println("\t📊 Total de tareas que fueron asignadas a procesadores: "+(this.qTask - this.taskQueue.size())+";");

        }



    }

    //puede no ser solucion optima si quedaron tareas en cola
    private void printEstateSolution(){
        boolean isOptimalSolution = this.isOptimalSolution();
        boolean isEmptyProccessors = this.isAreEmptyProccesors();

        if(!isOptimalSolution){
            System.out.println("\t\t\t❗❗❗❗❗❗❗ Error ❗❗❗❗❗❗❗");

            if(isEmptyProccessors){
                System.out.println("❌ Con estas restricciones hay procesadores que quedaron sin tareas asignadas y tambien quedaron tareas en cola.");
            }else {
                System.out.println("❗ Hay tareas que con estas restricciones no pueden ser asignadas. Al intentar resolver la asignacion quedaron "+this.taskQueue.size()+" tareas en cola.");
            }

        }else{
            System.out.println("\t\t\t✅ Solucion obtenida exitosamente ✅");

            if(isEmptyProccessors){
                System.out.println("🟡 Se asignaron todas las tareas y se minimizo el tiempo total. Con estas restricciones hay procesadores sin tareas asignadas.");
            }else{
                System.out.println("✅ Todas las tareas fueron asignadas utilizando todos los procesadores y minimizando el tiempo de ejecucion.");
            }
            System.out.println("\n📄 Lista de asignacion:\n");
            System.out.println(this.solution);
        }
    }

    private void getAssigment(Float x){
        for(Processor p : this.proccList){
            this.solution.put(p.getIdProc(),new ArrayList<>());
        }

        Processor p = this.getBetterProccesor(this.taskQueue.peek(),x);
        while(!this.taskQueue.isEmpty() && p != null){

            this.solution.get(p.getIdProc()).add(this.taskQueue.poll());

            if(!this.taskQueue.isEmpty()){
                p = this.getBetterProccesor(this.taskQueue.peek(),x);
            }

        }

    }

    private Processor getBetterProccesor(Task t, Float X) {
        ArrayList<Processor> possibles=new ArrayList<>();
        Processor pMin;

        for(Processor p: this.proccList){
            if(this.isFactible(p,t,X)){
                possibles.add(p);
            }
        }

        if(possibles.isEmpty()){
            return null;
        }

        pMin= this.getProccShortExectTime(possibles);

        return pMin;

    }

    //esta funcion evalua si un procesador es adecuado para la tarea.
    //si el procesador NO es refrigerado, se observa si el tiempo total de ejecucion que tiene al momento es menor que X
    //si el tiempo total de ejecucion que tiene al momento es menor que X, se evaluara si agregarle la tarea superará el tiempo X
    //si no supera el tiempo X agregarle la tarea, se observa si la tarea es critica. Si es critica retorna true/false si tiene menos de 2 tareas criticas
    //-->si la tarea no es critica y no supera el tiempo X agregarle la tarea al procc, el procesador no refrigerado es adecuado para la tarea.
    //si el procesador ES refrigerado, se observa si la tarea es critica
    //si la tarea es critica, retorno true/false si el procesador tiene menos de 2 tareas criticas
    //-->si la tarea no es critica, el procesador refrigerado se considera adecuado para la tarea.
    private boolean isFactible(Processor p, Task t,Float x){
        int MAX_CRITIC_TASKS = 2;
        boolean taskIsCritic= t.isEsCritica();
        int quantityCriticTaskInProcc= taskIsCritic ? this.getQuantityCriticTaskInProcc(p) : -1;
        boolean isTotalCriticTaskBelowLimit= quantityCriticTaskInProcc < MAX_CRITIC_TASKS;


        if(!p.isCooled()){
            float totalExecTimeTasksAssig= this.getTimeExectTaskList(this.solution.get(p.getIdProc()));
            boolean isTotalExecTimeLessX = ((totalExecTimeTasksAssig <= x) && (totalExecTimeTasksAssig + t.getTiempo_ejecucion() <= x));

            if(taskIsCritic){
                return isTotalExecTimeLessX && isTotalCriticTaskBelowLimit;
            }
            return isTotalExecTimeLessX;
        }

        //aca llegarian solo procesadores refrigerados
        if(taskIsCritic){
            return isTotalCriticTaskBelowLimit;
        }

        return true;
    }

    private Processor getProccShortExectTime(ArrayList<Processor> proccs){
        float accTimeEjec=this.getTimeExectTaskList(this.solution.get(proccs.getFirst().getIdProc()));
        Processor pMin=proccs.getFirst();

        for(Processor p: proccs){
            if(this.solution.get(p.getIdProc()).isEmpty()){
                return p;
            }else{
                if(this.getTimeExectTaskList(this.solution.get(p.getIdProc())) < accTimeEjec){
                    accTimeEjec= this.getTimeExectTaskList(this.solution.get(p.getIdProc()));
                    pMin=p;
                }
            }

        }

        return pMin;
    }

    //retorna la cantidad de tareas criticas que tiene asignado el procesador
    private int getQuantityCriticTaskInProcc(Processor p){
        int q=0;
        for(Task t : this.solution.get(p.getIdProc())){
            if(t.isEsCritica()){
                q++;
            }
        }
        return q;
    }

    //recibe una lista de tareas y retorna la sumatoria del tiempo de ejecucion
    private float getTimeExectTaskList (ArrayList<Task> tasksAssigs){
        float acc=0;
        for(Task t : tasksAssigs){
            acc= acc+ t.getTiempo_ejecucion();
        }
        return acc;
    }

    //retorna la info para imprimir del procesador mas cargado en cuanto a tiempo de ejecucion
    private ArrayList<String> getDataProccBigExecTime (){
        ArrayList<String> dataProcc = new ArrayList<>();
        float total=0;
        float tmp;

        for(Map.Entry<String, ArrayList<Task>> assig: this.solution.entrySet()){
            tmp=0;
            for(Task t : assig.getValue()){
                tmp= tmp+t.getTiempo_ejecucion();
            }
            if(tmp > total){
                total=tmp;
                dataProcc.clear();
                dataProcc.add(assig.getKey());
                dataProcc.add(String.valueOf(total));
            }
        }

        return dataProcc;
    }

    private boolean isOptimalSolution(){
        return this.taskQueue.isEmpty();
    }

    private boolean isAreEmptyProccesors(){
        for(Map.Entry<String, ArrayList<Task>> assig: this.solution.entrySet()){
            if(assig.getValue().isEmpty()){
                return true;
            }
        }
        return false;
    }

}