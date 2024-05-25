package tpe.filters;

import tpe.structures.*;

import java.util.ArrayList;
import java.util.Comparator;

public class SearchGroupPriority extends TreeTask {

    public SearchGroupPriority(){
        super();
        this.initTree();
    }

    @Override
    protected void initTree() {
        //obtiene una copia de la lista de referencias
        this.refListInOrder=this.getCopyRefList();

        //ordena la lista acorde al nivel de prioridad (mas prioritarias primero)
        refListInOrder.sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t2.getNivel_prioridad() - t1.getNivel_prioridad();
            }
        });

        //crea los nodos acorde a las referencias por prioridad
        this.references= this.helper.parseTaskToNodePriority(this.refListInOrder);

        //inserta cada nodo en el arbol balanceadamente acorde a la lista de referencias
        this.insertRefsInBalancedOrder();
    }

    @Override
    protected void add(Node node, Node newNode) {
        Node tmp;

        if(node.getId() > newNode.getId()){
            if(node.getLeft() == null){
                tmp= new Node(newNode.getId());
                this.addRefs(tmp,newNode);

                node.setLeft(tmp);

            }else{
                this.add(node.getLeft(),newNode);
            }

        } else if (node.getId() < newNode.getId()) {
            if(node.getRight() == null){
                tmp=new Node (newNode.getId());
                this.addRefs(tmp,newNode);

                node.setRight(tmp);

            }else{
                this.add(node.getRight(),newNode);
            }
        }

    }

    public ArrayList<Task> getTasksBetweenPriorities(int pMin, int pMax){
        ArrayList<Node> results= new ArrayList<>();
        findByPriorities(this.root,pMin,pMax,results);

        if(!results.isEmpty()){
            ArrayList<Task> tasks= new ArrayList<>();

            for(Node res: results){
                for(int index: res.getRefs()){
                    tasks.add(this.refListInOrder.get(index));
                }
            }

            return tasks;
        }

        return null;
    }

    private void findByPriorities(Node tmp, int min,int max, ArrayList<Node> res){

        if(tmp != null){
            if(tmp.getId() >= min && tmp.getId() <= max){
                res.add(tmp);
            }

            if(tmp.getLeft() != null && tmp.getLeft().getId() >= min){
                findByPriorities(tmp.getLeft(),min,max,res);

            } else if (tmp.getRight() != null && tmp.getRight().getId() <= max) {
                findByPriorities(tmp.getRight(),min,max,res);

            }

        }

    }

    private void addRefs(Node tmp, Node newNode){
        for(int value : newNode.getRefs()){
            tmp.addRef(value);
        }
    }

    public String toString(){
        return "Arbol de busqueda por Prioridad. Tareas ordenadas balanceadamente de acuerdo a su prioridad.";
    }
}
