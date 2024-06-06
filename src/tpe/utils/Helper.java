package tpe.utils;

import tpe.structures.Node;
import tpe.structures.Task;

import java.util.ArrayList;

public class Helper {

    public String caseSensitiveId (String id){
        if (id == null || id.isEmpty()) {
            return id;
        }

        char firstChar = Character.toUpperCase(id.charAt(0));
        String num = id.length() > 1 ? id.substring(1) : "";


        return firstChar + num;
    }


    public ArrayList<Node> parseTaskToNodePriority(ArrayList<Task> tasks){
        ArrayList<Node> result = new ArrayList<>();
        int priority=-1;
        int index=0;
        Node tmp;

        for(Task t : tasks){

            if(priority != t.getNivel_prioridad()){
                tmp = new Node(t.getNivel_prioridad());
                tmp.addRef(index);
                result.add(tmp);

            }else{
                tmp= result.getLast();
                tmp.addRef(index);
            }
            priority=t.getNivel_prioridad();
            index++;
        }

        return result;

    }


}
