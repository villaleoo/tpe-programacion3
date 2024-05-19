package tpe.controllers;

import tpe.filters.SearchTreeTaskId;
import tpe.schemes.Task;
import tpe.utils.Helper;

public class TaskController {
    //el task controller contendra los arboles binarios de busqueda de las tareas
    private SearchTreeTaskId treeID;
    private Helper helper;

    public TaskController(){
        this.helper=new Helper();
        this.treeID=new SearchTreeTaskId();
    }


    public Task findTask(String id){
        int index = this.helper.intTaskId(id);

        if(index >= 0){
            return this.treeID.getTask(index);
        }
        return null;
    }




}
