package tpe.filters;

import tpe.schemes.Node;
import tpe.schemes.Task;
import tpe.schemes.TreeTask;
import java.util.ArrayList;
import java.util.Collections;

public class SearchTaskId extends TreeTask {

    public SearchTaskId(){
        super();
        this.initTree();
    }

    @Override
    protected void initTree() {
        //obtiene una copia de la lista de referencia
        this.refListInOrder=this.getCopyRefList();

        //ordena la copia acorde a los ID
        Collections.sort(refListInOrder);

        //crea los nodos acorde a las referencias por id
        this.references = this.helper.parseTaskToNodeId(this.refListInOrder);

        //inserta cada nodo en el arbol balanceadamente acorde a la lista de referencias
        this.insertRefsInBalancedOrder();
    }

    @Override
    protected void add(Node node, Node newNode) {
        Node tmp;

        if(node.getId() > newNode.getId()){
            if(node.getLeft() == null){
                tmp= new Node(newNode.getId());
                tmp.addRef(newNode.getRefs().getFirst());
                node.setLeft(tmp);

            }else{
                this.add(node.getLeft(),newNode);
            }

        } else if (node.getId() < newNode.getId()) {
            if(node.getRight() == null){
                tmp=new Node (newNode.getId());
                tmp.addRef(newNode.getRefs().getFirst());
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
            if(node.getId() == idSearch){
                Node tmp = new Node(node.getId());
                tmp.addRef(node.getRefs().getFirst());

                return tmp;

            } else if (node.getId() > idSearch) {

                return getById(node.getLeft(),idSearch);

            } else {
                return getById(node.getRight(),idSearch);

            }
        }
    }

    private Task parseToTask(Node node){
        int index= node.getRefs().getFirst();
        Task result= this.refListInOrder.get( index );

        return new Task(result.getId_tarea(),result.getNombre_tarea(),result.getTiempo_ejecucion(), result.isEsCritica(), result.getNivel_prioridad());
    }

    public String toString(){
        return "Arbol de busqueda por ID. Tareas ordenadas balanceadamente de acuerdo a su ID.";
    }
}