package tpe.utils;

import tpe.schemes.NodeSimple;
import tpe.schemes.Task;

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

    public ArrayList<NodeSimple> parseToNodeTaskId(ArrayList<Task> tasks){
        ArrayList<NodeSimple> result=new ArrayList<>();
        int index=0;

        for(Task t : tasks){
           NodeSimple node = new NodeSimple(this.intTaskId(t.getId_tarea()),index);
           result.add(node);
           index++;
        }

        return result;
    }


}