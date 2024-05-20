package tpe.schemes;

import java.util.ArrayList;

public class Node {
    private int id;
    private ArrayList<Integer> refs;
    private Node left,right;

    public Node(int id){
        this.id=id;
        this.refs=new ArrayList<>();
        this.left=null;
        this.right=null;
    }

    public void addRef(int i){
        this.refs.add(i);
    }

    public int getId(){
        return this.id;
    }

    public ArrayList<Integer> getRefs() {
        return new ArrayList<>(this.refs);
    }


    public Node getRight() {
        return this.right;
    }


    public Node getLeft() {return  this.left;}


    public void setLeft(Node leftNode) {
        this.left = leftNode;
    }


    public void setRight(Node rightNode) {
        this.right=rightNode;
    }

    @Override
    public String toString(){
        return "\nidentificador: "+this.getId()+";\nIndex/posiciones en la lista de referencia ordenada: "+this.getRefs()+";\n";
    }

}
