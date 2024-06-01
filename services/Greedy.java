package tpe.services;

import tpe.structures.Processor;
import tpe.structures.Task;
import tpe.utils.CSVReader;

import java.lang.reflect.Array;
import java.util.*;

//para resolver el problema por greedy se elaboraron los siguientes pasos:
//1) se armo una cola de tareas ordenadas por tiempo de ejecucion (tareas con mayor tiempo de ejecucion primero).
//2) se creo una funcion printAssigment para mostrar la solucion obtenida y las metricas.
//3) se creo la funcion getAssigment que seria la funcion de estructura general/clasica de greedy:
    //se tiene un conjunto de solucion inicialmente vacio (solo con los procesadores cargados, sin tareas asignadas)
    //se itera la cola de tareas (candidatos) hasta llegar a estar vacia. En esta iteracion se determina si es factible asignar un procesador a la primer tarea de la cola
    //la cola de tareas va disminuyendo a medida que el algoritmo va encontrando procesadores adecuados para esa tarea (funcion betterProcc)

//4)la funcion betterProcc recibe una tarea (la primera de la cola) de getAssigment e itera los procesadores buscando los adecuados para asignarle esa tarea (funcion greedyIsFact).
    //para determinar si un procesador es adecuado para una tarea, se creo la funcion greedyIsFact ---> de acuerdo a una tarea y un procesador, determina si
    //el procesador cumple con los requisitos para asignarle esa tarea.
    //betterProcc a medida que va encontrando procesadores aptos, los va agregando a una lista de procesadores posibles
    //cuando termina de obtener la lista de los procesadores posibles, se los envia a la funcion getProcMinTimeEjec para obtener, entre los posibles procesadores,
    //el procesador que no tiene ninguna tarea asignada (seria el mejor para minimizar tiempos) ó el que menor tiempo de ejecucion tiene al momento de asignar la tarea.

//5) una vez que se determina el mejor procesador para la primer tarea de la cola siguiendo los pasos del punto (4), getAssigment consigue el mejor procesador para
//la primer tarea en la cola, por lo que asigna la tarea al procesador en la estructura de solucion (HashMap solution) y elimina la tarea de la cola.

//SE LLEGA A LA SOLUCION UNA VEZ QUE LA COLA DE TAREAS ESTA VACIA. SE ITERA ADA TAREA, BUSCANDO EL PROCESADOR ADECUADO QUE MINIMIZA EL TIEMPO DE EJECUCION
//getAssigment -> itera cada tarea para asignarle el procesador que minimiza el tiempo de ejecucion total.
//betterProcc -> itera cada procesador para encontrar el conjunto de procesadores que se les puede asignar la tarea.
//greedyIsFact -> la utiliza betterProcc para determinar cual procesador es apto para la tarea y cual no es apto.
//getProcMinTimeEjec -> la utiliza betterProcc para determinar el procesador que minimiza el tiempo de ejecucion total. Trabaja con procesadores que son adecuados para 1 tarea

//EL TIEMPO DE EJECUCION DE UN PROCESADOR ES LA SUMATORIA DEL TIEMPO DE EJECUCION DE LAS TAREAS QUE TIENE ASIGNADO.

public class Greedy {
    ArrayList<Processor> proccList;
    Queue<Task> taskQueue;
    HashMap<String, ArrayList<Task>> solution;
    int qTask;

    public Greedy(String pathProcessor, String pathTasks){
        CSVReader reader= new CSVReader();
        ArrayList<Task> tasksList= reader.getTasks();
        reader.readProcessors(pathProcessor);
        reader.readTasks(pathTasks);

        this.proccList=new ArrayList<>(reader.getProcessors());
        this.solution=new HashMap<>();
        this.orderList(tasksList);
        this.taskQueue= new LinkedList<>(tasksList);
        this.qTask=this.taskQueue.size();

    }

    private void orderList (ArrayList<Task> tasks){
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

        ArrayList<String> dataProccBigTime= this.getDataProccBigTime();
        System.out.println("\n\tEl procesador con mayor tiempo de ejecucion es: id_procesador = "+dataProccBigTime.getFirst()+";");
        System.out.println("\tTIEMPO TOTAL DE EJECUCION : "+dataProccBigTime.getLast()+";");
        System.out.println("\tTOTAL DE TAREAS (candidatos) ASIGNADAS A PROCESADORES: "+this.qTask+";");
        System.out.println("\nLista de asignacion de tareas a procesadores:\n");
        System.out.println(this.solution);

    }

    //primero agrega todos los procesadres a la solucion, dado que todos los procesadores deben hacer al menos una tarea.
    //itera la cola de tareas, buscando el mejor procesador para la tarea ubicada primera (tareas ordenadas por tiempo de ejecucion y prioridad)
    private void getAssigment(Float x){
        for(Processor p : this.proccList){
            this.solution.put(p.getIdProc(),new ArrayList<>());
        }

       while(!this.taskQueue.isEmpty()){
           Processor p = this.betterProcc(this.taskQueue.peek(),x);

           this.solution.get(p.getIdProc()).add(this.taskQueue.poll());

       }

    }

    //esta funcion evalua si un procesador es adecuado para la tarea.
        //si el procesador NO es refrigerado, se observa si el tiempo total de ejecucion que tiene al momento es menor que X
            //si el tiempo total de ejecucion que tiene al momento es menor que X, se evaluara si agregarle la tarea superará el tiempo X
            //si no supera el tiempo X agregarle la tarea, se observa si la tarea es critica. Si es critica retorna true/false si tiene menos de 2 tareas criticas
            //-->si la tarea no es critica y no supera el tiempo X agregarle la tarea al procc, el procesador no refrigerado es adecuado para la tarea.
        //si el procesador ES refrigerado, se observa si la tarea es critica
            //si la tarea es critica, retorno true/false si el procesador tiene menos de 2 tareas criticas
            //-->si la tarea no es critica, el procesador refrigerado se considera adecuado para la tarea.
    private boolean greedyIsFact(Processor p, Task t,Float x){
        float totalTimeEjectP= this.totalTimeEject(this.solution.get(p.getIdProc()));

        if(!p.isCooled()){
            if(totalTimeEjectP <= x){
                if(totalTimeEjectP + t.getTiempo_ejecucion() > x){
                    return false;
                }else{
                    if((t.isEsCritica())){
                        return this.getQuantityCriticTaskInProcc(p) < 2;
                    }
                    return true;
                }
            }
        }else{
            if(t.isEsCritica()){
                return this.getQuantityCriticTaskInProcc(p) < 2;
            }
            return true;
        }

        return false;
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

    //Esta funcion primero busca los procesadores que son candidatos a poder asignarle las tareas y los agrega a la lista de posibles
    //Cuando obtiene la lista de los que son adecuados para la tarea, se la pasa a otra funcion que busca y
    //obtiene el procesador con el tiempo minimo de ejecucion al momento, con el fin de achicar el tiempo maximo de ejecucion
    private Processor betterProcc(Task t, Float X) {
        ArrayList<Processor> possibles=new ArrayList<>();
        Processor pMin;

        for(Processor p: this.proccList){
            if(this.greedyIsFact(p,t,X)){
                possibles.add(p);
            }
        }

        pMin= this.getProcMinTimeEjec(possibles);

        return pMin;

    }

    //entre los procesadores que cumplen los requisitos para asignarle la tarea,
    //retorna el procesador que se encuentra vacio Ó el que tiene menor tiempo de ejecucion al momento
    private Processor getProcMinTimeEjec(ArrayList<Processor> proccs){
        float accTimeEjec=this.totalTimeEject(this.solution.get(proccs.getFirst().getIdProc()));
        Processor pMin=proccs.getFirst();

        for(Processor p: proccs){
            if(this.solution.get(p.getIdProc()).isEmpty()){
                return p;
            }else{
                if(this.totalTimeEject(this.solution.get(p.getIdProc())) < accTimeEjec){
                    pMin=p;
                }
            }

        }

        return pMin;
    }

    //recibe una lista de tareas y retorna la sumatoria del tiempo de ejecucion
    private float totalTimeEject (ArrayList<Task> tasksAssigs){
        float acc=0;
        for(Task t : tasksAssigs){
            acc= acc+ t.getTiempo_ejecucion();
        }
        return acc;
    }


    private ArrayList<String> getDataProccBigTime (){
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



}





