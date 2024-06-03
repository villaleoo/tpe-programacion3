package tpe.structures;

import tpe.utils.Helper;

import java.util.ArrayList;

//PASOS BASICOS QUE HACEN LOS ARBOLES DEL SERVICIO 1 Y 3:

//EL CSVReader le manda la lista de tareas EN ORDEN DE COMO LAS LEE (refList)
//LA CLASE PADRE/ABSTRACTA OBTIENE LA refList --> ESTATICA para que sea para todos la misma lista
//esta hecho con clase abstracta para posibilidad de agregar mas arboles de busqueda con otros campos de ordenamiento

//CADA CLASE HIJA DE TreeTask:
//1) se encarga de ordenar la refList (refListInOrder)--> paso que podria omitirse y solo trabajar con refList. Lo inclui para servicio de traer todas las tareas ordenadas por...
//2) se encarga de convertir la refListOrder en una nueva lista de referencias de tipo <Node> (references).
//Cada elemento de esta lista (references) almacena el ID (prioridad) ) y Values (index's de la lista refListOrder de la/las tareas con esa prioridad).
//3)luego de armar la lista references, busca EL ORDEN EN EL QUE SE DEBEN ALMACENAR EN EL ARBOL las referencias (metodo getInsertionOrder)
//4) al encontrar y guardar el orden de agregación, se invoca al metodo add (agregar al arbol) por cada elemento (metodo insertRefsInBalancedOrder)
//5) los pasos anteriores se ejecutan al instanciar un arbol, dado que son esenciales para el funcionamiento correcto del arbol (metodo initTree) .




public abstract class TreeTask {
    protected static ArrayList<Task> refList;
    protected Node root;
    protected Helper helper;
    protected ArrayList<Task> refListInOrder;
    protected ArrayList<Node> references;


    public TreeTask(){
        this.helper=new Helper();
    }

    public static void setRefList(ArrayList<Task> ref){
        if(refList == null){
            refList=new ArrayList<>(ref);
        }else{
            refList.clear();
            refList.addAll(ref);
        }
    }

    protected void insert(Node node) {
        if (this.root == null) {
            this.root = node;
        } else {
            this.add(this.root,node);
        }

    }

    protected ArrayList<Task> getCopyRefList(){
        return new ArrayList<>(refList);
    }

    public boolean isEmpty() { return this.root == null; }

    public int getHeight() {
        return this.isEmpty() ? 0 : this.getH(this.root);
    }

    public void printPosOrder() {
        this.posOrder(this.root);
    }

    public void printPreOrder() {
        this.preOrder(this.root);
    }

    public void printInOrder() {
        this.inOrder(this.root);
    }

    protected int getH(Node node) {
        if (node == null) {
            return -1;
        } else {
            int hLeft = this.getH(node.getLeft());
            int hRight = this.getH(node.getRight());

            return hLeft >= hRight ? hLeft + 1 : hRight + 1;
        }
    }

    protected void posOrder(Node node) {
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

        System.out.println(node.getId());
    }

    protected void preOrder(Node node) {
        System.out.println(node.getId());

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

    protected void inOrder(Node node) {
        if (node.getLeft() != null) {
            this.inOrder(node.getLeft());
        } else {
            System.out.println("-");
        }

        System.out.println(node.getId());
        if (node.getRight() != null) {
            this.inOrder(node.getRight());
        } else {
            System.out.println("-");
        }

    }

    protected boolean hasElement(int id) {
        Node tmp = this.root;
        boolean hasElem = false;

        while(tmp != null && !hasElem) {

            hasElem = tmp.getId() == id;

            if (!hasElem) {
                if (tmp.getId() < id) {
                    tmp = tmp.getRight();
                } else {
                    tmp = tmp.getLeft();
                }
            }

        }
        return hasElem;
    }

    protected void insertRefsInBalancedOrder() {
        ArrayList<Node> listOrderInsert =new ArrayList<>();
        this.getInsertionOrder(0,this.getReferences().size()-1,listOrderInsert);


        for(Node node: listOrderInsert){
            this.insert(node);
        }
    }

    protected void getInsertionOrder(int i, int f, ArrayList<Node> tmp){
        int middle;

        if (f-i == 0) {
            tmp.add(this.getReferences().get(i));

        }else if (i < f){
            middle=(i+f)/2;
            tmp.add(this.getReferences().get(middle));

            getInsertionOrder(i,middle-1,tmp);
            getInsertionOrder(middle+1,f,tmp);
        }
    }

    protected ArrayList<Node> getReferences(){ return this.references;};

    protected abstract void add(Node node, Node newNode);
    protected abstract void initTree();

}
