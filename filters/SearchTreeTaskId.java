package tpe.filters;

import tpe.schemes.Node;
import tpe.schemes.NodeSimple;
import tpe.schemes.Task;
import tpe.schemes.TreeTask;
import java.util.ArrayList;
import java.util.Collections;

public class SearchTreeTaskId extends TreeTask {
    private ArrayList<Task> refListOrder;
    private ArrayList<NodeSimple> references;

    public SearchTreeTaskId(){
        super();
        this.refListOrder=this.getCopyRefList();
        Collections.sort(refListOrder);
        this.references = this.helper.parseToNodeTaskId(this.refListOrder);
        this.insertRefInBalancedOrder();
    }

    @Override
    protected void insertRefInBalancedOrder() {
        ArrayList<NodeSimple> listOrderInsert =new ArrayList<>();
        getInsertionOrder(0,this.references.size()-1,listOrderInsert);

        for(NodeSimple node: listOrderInsert){
            this.insert(node);
        }
    }

    @Override
    protected void add(Node node, Node newNode) {
        Node tmp;

        if(node.getID() > newNode.getID()){
            if(node.getLeft() == null){
                tmp= new NodeSimple(newNode.getID(), (int) newNode.getValue());
                node.setLeft(tmp);

            }else{
                this.add(node.getLeft(),newNode);
            }

        } else if (node.getID() < newNode.getID()) {
            if(node.getRight() == null){
                tmp=new NodeSimple(newNode.getID(),(int) newNode.getValue());
                node.setRight(tmp);

            }else{
                this.add(node.getRight(),newNode);
            }
        }

    }

    public Task getTask(int id){
       Node result = this.getById(this.root,id);

       if(result != null){
           return parseToTask(result);
       }
       return null;
    }

    private Node getById(Node node, int idSearch ){

        if(node == null){
            return null;

        }else{
            if(node.getID() == idSearch){

                return new NodeSimple(node.getID(),(int) node.getValue());

            } else if (node.getID() > idSearch) {

                return getById(node.getLeft(),idSearch);

            } else {
                return getById(node.getRight(),idSearch);

            }
        }
    }

    private void getInsertionOrder(int i, int f, ArrayList<NodeSimple> tmp){
        int middle;

        if (f-i == 0) {
            tmp.add(this.references.get(i));

        }else if (i < f){
            middle=(i+f)/2;
            tmp.add(this.references.get(middle));

            getInsertionOrder(i,middle-1,tmp);
            getInsertionOrder(middle+1,f,tmp);
        }
    }

    private Task parseToTask(Node node){
        int index= (int) node.getValue();
        Task result= this.refListOrder.get( index );

        return new Task(result.getId_tarea(),result.getNombre_tarea(),result.getTiempo_ejecucion(), result.isEsCritica(), result.getNivel_prioridad());
    }

    public String toString(){
        return "Arbol de busqueda por ID. Tareas ordenadas balanceadamente de acuerdo a su ID.";
    }
}