package tpe.filters;

import tpe.schemes.Task;
import tpe.schemes.TreeSearchTask;
import tpe.utils.Helper;
import java.util.ArrayList;

//esta clase es un arbol binario de busqueda ordenado por ID de tareas. Contiene operaciones asociadas al ID de las tareas: buscar tareas por ID,
//buscar tareas entre ciertos rangos de ID, listar todas las tareas ordenadas por ID, etc.

public class TreeSearchTaskID implements TreeSearchTask {
    private static ArrayList<Task> listaRef;
    private Task root ;
    private Helper helper;



    public TreeSearchTaskID(){
        //cuando instancio un arbol de estos, lo primero es ordenar las tareas de la lista principal (contenida en TreeSearch).
        this.helper=new Helper();

    }



    public static void setListaRef(ArrayList<Task> list) {
        listaRef=new ArrayList<>(list);
    }

    private void initTree(){
    
    }


    private Task getRoot() {
        return this.root;
    }

    //comprueba si un elemento(tarea) esta en el arbol a traves de la parte numerica del ID
    @Override
    public boolean hasElement(int taskId) {
        Task tmp = this.root;
        boolean hasElem = false;


        while(tmp != null && !hasElem) {
            int idThis= this.helper.intTaskId(tmp.getId_tarea());

            hasElem = this.isElement(tmp, taskId);

            if (!hasElem) {
                if (idThis < taskId) {
                    tmp = tmp.getRight();
                } else {
                    tmp = tmp.getLeft();
                }
            }
        }

        return hasElem;

    }

    //verifica si la parte numerica del id de una tarea, coincide con un id en particular
    private boolean isElement(Task t, int id) {
        return this.helper.intTaskId(t.getId_tarea()) == id;
    }



    @Override
    public boolean isEmpty() { return this.root == null; }

    //inserta una nueva tarea al arbol, la tarea debe ser creada por fuera (puede ser en el controller)
    @Override
    public void insert(Task task) {
        if (this.root == null) {
            this.root = task;
        } else {
            this.add(this.root,task);
        }

    }

    //agrega la nueva tarea al arbol binario de busqueda en base a SU VALOR NUMERICO DE ID:
    //Nueva tarea con Parte numerica de ID menores al de la raiz, se agrega a la izquierda de la raiz;
    //parte numerica de ID mayores al de la raiz, se agregan a su derecha.

    private void add(Task node, Task newTask) {
        Task temp;
        int nodeId= this.helper.intTaskId(node.getId_tarea());
        int newTaskId= this.helper.intTaskId(newTask.getId_tarea());

        if (nodeId > newTaskId) {
            if (node.getLeft() == null) {
                temp = new Task(newTask.getId_tarea(),newTask.getNombre_tarea(),newTask.getTiempo_ejecucion(),newTask.isEsCritica(),newTask.getNivel_prioridad());
                node.setLeft(temp);
            } else {
                this.add(node.getLeft(), newTask);
            }

        } else if (nodeId < newTaskId) {
            if (node.getRight() == null) {
                temp = new Task(newTask.getId_tarea(),newTask.getNombre_tarea(),newTask.getTiempo_ejecucion(),newTask.isEsCritica(),newTask.getNivel_prioridad());
                node.setRight(temp);
            } else {
                this.add(node.getRight(), newTask);
            }
        }

    }


    //llama a una funcion recursiva para retornar la altura del arbol binario de busqueda ordenado por los ID de las tareas
    @Override
    public int getHeight() {
        return this.isEmpty() ? 0 : this.getH(this.root);
    }

    private int getH(Task node) {
        if (node == null) {
            return -1;
        } else {
            int hLeft = this.getH(node.getLeft());
            int hRight = this.getH(node.getRight());

            return hLeft >= hRight ? hLeft + 1 : hRight + 1;
        }
    }

    //llama a una funcion recursiva para imprimir el arbol ordenado por ID's, en funcio del algoritmo pos-order
    @Override
    public void printPosOrder() {
        this.posOrder(this.root);
    }

    private void posOrder(Task node) {
        if (node.getLeft() != null) {
            this.posOrder(node.getLeft());
        } else {
            System.out.println("-");
        }

        if (node.getRight() != null) {
            this.posOrder(node.getRight());
        } else {
            System.out.println("-");
        }

        System.out.println(node.getNombre_tarea());
    }

    //llama a una funcion recursiva para imprimir el arbol ordenado por ID's, en funcio del algoritmo pre-order
    @Override
    public void printPreOrder() {
        this.preOrder(this.root);
    }

    private void preOrder(Task node) {
        System.out.println(node.getNombre_tarea());

        if (node.getLeft() != null) {
            this.preOrder(node.getLeft());
        } else {
            System.out.println("-");
        }

        if (node.getRight() != null) {
            this.preOrder(node.getRight());
        } else {
            System.out.println("-");
        }

    }

    //llama a una funcion recursiva para imprimir el arbol ordenado por ID's, en funcio del algoritmo in-order
    @Override
    public void printInOrder() {
        this.inOrder(this.root);
    }

    private void inOrder(Task node) {
        if (node.getLeft() != null) {
            this.inOrder(node.getLeft());
        } else {
            System.out.println("-");
        }

        System.out.println(node.getNombre_tarea());
        if (node.getRight() != null) {
            this.inOrder(node.getRight());
        } else {
            System.out.println("-");
        }

    }
}
