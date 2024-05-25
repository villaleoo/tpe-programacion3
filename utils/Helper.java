package tpe.utils;

import tpe.structures.Node;
import tpe.structures.Task;

import java.util.ArrayList;

public class Helper {

    public int intTaskId(String id){
        String pattern = "[tT](\\d+)";
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(id);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }

    public ArrayList<Node> parseTaskToNodeId(ArrayList<Task> tasks){
        ArrayList<Node> result=new ArrayList<>();
        int index=0;

        for(Task t : tasks){
           Node node = new Node(this.intTaskId(t.getId_tarea()));
           node.addRef(index);
           result.add(node);
           index++;
        }

        return result;
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
