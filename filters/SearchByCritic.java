package tpe.filters;

import tpe.structures.Task;

import java.util.ArrayList;

public class SearchByCritic {
    private static final ArrayList<Task> criticTasks = new ArrayList<Task>();
    private static final ArrayList<Task> notCriticTasks = new ArrayList<Task>();

    public static void addCriticTask(Task t){
        if (!criticTasks.contains(t))
            criticTasks.add(t);
    }

    public static void addNotCriticTask(Task t){
        if (!notCriticTasks.contains(t))
            notCriticTasks.add(t);
    }


    public static ArrayList<Task> getCriticTasks() {
        return criticTasks;
    }

    public static ArrayList<Task> getNotCriticTasks() {
        return notCriticTasks;
    }
}
