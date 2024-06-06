package tpe.controllers;

import tpe.filters.SearchByCritic;
import tpe.filters.SearchById;
import tpe.filters.SearchGroupPriority;
import tpe.structures.Task;
import tpe.utils.Helper;

import java.util.ArrayList;

public class TaskController {
    private Helper helper;

    public TaskController() {
        this.helper = new Helper();
    }


    public ArrayList<Task> findByPriorities(int pMin, int pMax) {
        SearchGroupPriority treePriority = new SearchGroupPriority();

        return treePriority.getTasksBetweenPriorities(pMin, pMax);
    }

    public Task findTaskById(String taskId) {
        String id = this.helper.caseSensitiveId(taskId);
        return SearchById.searchById(id);
    }

    public ArrayList<Task> findCriticTasks() {
        return SearchByCritic.getCriticTasks();
    }

    public ArrayList<Task> findNotCriticTasks() {

        return SearchByCritic.getNotCriticTasks();
    }
}
