package tpe.controllers;

import tpe.filters.SearchGroupPriority;
import tpe.schemes.Task;
import tpe.utils.Helper;

import java.util.ArrayList;

public class TaskController {
    //el task controller contendra los arboles binarios de busqueda de las tareas
    private Helper helper;

    public TaskController(){
        this.helper=new Helper();

    }


    public Task findTask(String id){

        return null;
    }

    public ArrayList<Task> findByPriorities(int pMin,int pMax){
        SearchGroupPriority treePriority = new SearchGroupPriority();

        return treePriority.getTasksBetweenPriorities(pMin,pMax);
    }




}
