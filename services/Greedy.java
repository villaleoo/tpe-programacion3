package tpe.services;

import tpe.structures.Processor;
import tpe.structures.Task;
import tpe.utils.CSVReader;

import java.util.*;


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

//SE LLEGA A LA SOLUCION UNA VEZ QUE LA COLA DE TAREAS ESTA VACIA. SE ITERA CADA TAREA, BUSCANDO EL PROCESADOR ADECUADO QUE MINIMIZA EL TIEMPO DE EJECUCION.
//EL TIEMPO DE EJECUCION DE UN PROCESADOR ES LA SUMATORIA DEL TIEMPO DE EJECUCION DE LAS TAREAS QUE TIENE ASIGNADO.

//printAssigment -> llama a la funcion que asigna tareas getAssigment. Si se hace prueba con lista de tareas que excedan los requisitos muestra error.
//getAssigment -> itera cada tarea para asignarle el procesador que minimiza el tiempo de ejecucion total.
//getBetterProccesor -> itera cada procesador para encontrar el conjunto de procesadores que se les puede asignar la tarea.
//isFactible -> la utiliza getBetterProccesor para determinar cual procesador es apto para la tarea y cual no es apto.
//getProccShortExectTime -> la utiliza getBetterProccesor para determinar el procesador que minimiza el tiempo de ejecucion total. Trabaja con procesadores que son adecuados para 1 tarea
// isOptimalSolution -> determina si la solucion alcanzada es optima. Con funciones auxiliares diferencia si hay procesadores que no se le incluyeron tareas ó si hay tareas
        //que no se pudieron asignar a ningun procesador.
        //Lo que influye en que la solucion no sea optima es principalmente el valor del numero X y la cantidad de tareas criticas en cola.



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

        if(!this.isOptimalSolution()){
            //puede no ser solucion optima si hay procesadores vacios ó quedaron tareas en cola
            System.out.println("\t\t\t❗❗❗❗❗❗❗ Error ❗❗❗❗❗❗❗\t\t\t");
            if(this.isAreEmptyProccesors()){
                System.out.println("🟡 Aunque se asignaron todas las tareas, con estas restricciones hay procesadores sin tareas asignadas.");
            }
            if(!this.taskQueue.isEmpty()){
                System.out.println("❗ Hay tareas que con estas restricciones no pueden ser asignadas. Quedaron "+this.taskQueue.size()+" tareas en cola.");
            }

            System.out.println("Solucion parcial obtenida: \n");
        }else{
            System.out.println("\t\t\t✅Solucion obtenida exitosamente (todas las tareas asignadas)✅");
        }

        System.out.println("\n\t📈 El procesador con mayor tiempo de ejecucion es: id_procesador = "+dataProccBigTime.getFirst()+";");
        System.out.println("\t⌚ Tiempo total de ejecucion: "+dataProccBigTime.getLast()+". (sumatoria de tiempo_ejecucion de las tareas en el "+dataProccBigTime.getFirst()+");");
        System.out.println("\t📄 Total de tareas en cola previo a comenzar la asignacion: "+this.qTask+";");
        System.out.println("\t📊 Total de tareas que fueron asignadas a procesadores: "+(this.qTask - this.taskQueue.size())+";");
        if(!this.taskQueue.isEmpty()){
            System.out.println("\t❌ Tareas que quedaron en cola (sin asignar): "+this.taskQueue.size()+";");
        }
        System.out.println("\n📄 Lista de asignacion:\n");
        System.out.println(this.solution);




    }

    //primero agrega todos los procesadres a la solucion, dado que todos los procesadores deben hacer al menos una tarea.
    //itera la cola de tareas, buscando el mejor procesador para la tarea ubicada primera (tareas ordenadas por tiempo de ejecucion y prioridad)
    private void getAssigment(Float x){
        for(Processor p : this.proccList){
            this.solution.put(p.getIdProc(),new ArrayList<>());
        }


        while(!this.taskQueue.isEmpty()){
            Processor p = this.getBetterProccesor(this.taskQueue.peek(),x);

            if(p == null){
                return;
            }

            this.solution.get(p.getIdProc()).add(this.taskQueue.poll());

        }


       //si la capacidad de los no refrigerados esta a tope && (todos los refrigerados tienen 2 criticas && la que viene es critica) || la que viene es critica y quedan procesadores sin 2 criticas

    }

    //Esta funcion primero busca los procesadores que son candidatos a poder asignarle las tareas y los agrega a la lista de posibles
    //Cuando obtiene la lista de los que son adecuados para la tarea, se la pasa a otra funcion que busca y
    //obtiene el procesador con el tiempo minimo de ejecucion al momento, con el fin de achicar el tiempo maximo de ejecucion
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
        float getTimeExectTaskListP= this.getTimeExectTaskList(this.solution.get(p.getIdProc()));

        int MAX_CRITIC_TASKS = 2;
        if(!p.isCooled()){
            if(getTimeExectTaskListP <= x){
                if(getTimeExectTaskListP + t.getTiempo_ejecucion() > x){
                    return false;
                }else{
                    if((t.isEsCritica())){
                        return this.getQuantityCriticTaskInProcc(p) < MAX_CRITIC_TASKS;
                    }
                    return true;
                }
            }
        }else{
            if(t.isEsCritica()){
                return this.getQuantityCriticTaskInProcc(p) < MAX_CRITIC_TASKS;
            }
            return true;
        }

        return false;
    }
    
    //entre los procesadores que cumplen los requisitos para asignarle la tarea,
    //retorna el procesador que se encuentra vacio Ó el que tiene menor tiempo de ejecucion al momento
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
        return !this.isAreEmptyProccesors() && this.taskQueue.isEmpty();
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





