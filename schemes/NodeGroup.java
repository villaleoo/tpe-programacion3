package tpe.schemes;

import java.util.ArrayList;

public class NodeGroup implements Node{
    private int id;
    private ArrayList<Integer> refs;
    private NodeGroup left,right;

    public NodeGroup(int id){
        this.id=id;
        this.refs=new ArrayList<>();
        this.left=null;
        this.right=null;
    }

    public void addValue(int i){
        this.refs.add(i);
    }

    @Override
    public ArrayList<Integer> getValue() {
        return new ArrayList<>(this.refs);
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public NodeGroup getRight() {
        return this.right;
    }

    @Override
    public NodeGroup getLeft() {
        return this.left;
    }

    @Override
    public void setLeft(Object leftNode) {
        this.left = (NodeGroup) leftNode;
    }

    @Override
    public void setRight(Object rightNode) {
        this.right= (NodeGroup) rightNode;
    }
}
