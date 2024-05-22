package tpe.filters;

import tpe.schemes.Task;

import java.util.HashMap;

public class SearchByCritic {
    private static HashMap<String, Task> criticTasks;
    private static HashMap<String, Task> notCriticTasks;

    public static void addCriticTask(Task t){
        if(criticTasks == null){
            criticTasks=new HashMap<>();
        }
        if(t.isEsCritica()){
            criticTasks.put(t.getId_tarea(),t);
        }
    }

    public static void addNotCriticTask(Task t){
        if(notCriticTasks == null){
            notCriticTasks=new HashMap<>();
        }
        if(!t.isEsCritica()){
            notCriticTasks.put(t.getId_tarea(),t);
        }
    }
    //faltaria crear la funcion para obtener la lista de tareas que se van a buscar en uno de los 2 hashMap
}
