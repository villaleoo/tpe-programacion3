package tpe.schemes;

import tpe.utils.Helper;

import java.util.ArrayList;

public abstract class TreeTask {
    protected Node root;
    protected Helper helper;
    protected static ArrayList<Task> refList;

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

    protected ArrayList<Task> getCopyRefList(){
        return new ArrayList<>(refList);
    }

    public boolean isEmpty() { return this.root == null; }

    //inserta un nuevo nodo al arbol, llama a la funcion recursiva add implementada en cada arbol hijo
    protected void insert(Node node) {
        if (this.root == null) {
            this.root = node;
        } else {
            this.add(this.root,node);
        }

    }

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

        System.out.println(node.getID());
    }

    protected void preOrder(Node node) {
        System.out.println(node.getID());

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

        System.out.println(node.getID());
        if (node.getRight() != null) {
            this.inOrder(node.getRight());
        } else {
            System.out.println("-");
        }

    }

    protected boolean hasElement(int id) {
        Node tmp = this.root;
        boolean hasElem = false;

        //como arranca en el root quizas nunca se meta en el tmp.getLeft(); ->> esto no es asi porque el root no es el menor de todos, es el del medio 📌
        while(tmp != null && !hasElem) {

            hasElem = tmp.getID() == id;

            if (!hasElem) {
                if (tmp.getID() < id) {
                    tmp = tmp.getRight();
                } else {
                    tmp = tmp.getLeft();
                }
            }

        }
        return hasElem;
    }

    protected abstract void add(Node node, Node newNodeTask);
    protected abstract void insertRefInBalancedOrder();
}
