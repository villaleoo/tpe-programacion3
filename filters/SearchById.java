package tpe.filters;

import tpe.structures.Task;

import java.util.HashMap;

public class SearchById {
    private static final HashMap<String, Task> indexTasks = new HashMap<>();

    public static void addTask(Task t, String taskId) {
        if (!indexTasks.containsKey(taskId))
            indexTasks.put(taskId, t);
    }

    public static Task searchById(String taskId) {

        if (!indexTasks.containsKey(taskId))
            return null;

        return indexTasks.get(taskId);
    }
}
