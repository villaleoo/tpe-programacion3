package tpe.schemes;

import tpe.utils.Helper;

import java.util.ArrayList;

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

    protected void insertRefsInBalancedOrder() {
        ArrayList<Node> listOrderInsert =new ArrayList<>();
        this.getInsertionOrder(0,this.getReferences().size()-1,listOrderInsert);


        for(Node node: listOrderInsert){
            this.insert(node);
        }
    }

    protected ArrayList<Node> getReferences(){
        return this.references;
    };

    protected abstract void add(Node node, Node newNode);
    protected abstract void initTree();

}
