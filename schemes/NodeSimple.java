package tpe.schemes;

public class NodeSimple implements Node{
    private int numericID;
    private int indexRef;
    private NodeSimple left,right;

    public NodeSimple(int numericID, int indexRef){
        this.numericID=numericID;
        this.indexRef=indexRef;
        this.left=null;
        this.right=null;
    }

    @Override
    public Integer getValue() {
        return indexRef;
    }

    @Override
    public int getID() {
        return numericID;
    }

    @Override
    public NodeSimple getRight() {
        return this.right;
    }

    @Override
    public NodeSimple getLeft() {
        return this.left;
    }

    @Override
    public void setLeft(Object leftNode) {
        this.left = (NodeSimple) leftNode;
    }

    @Override
    public void setRight(Object rightNode) {
        this.right= (NodeSimple) rightNode;
    }


    public String toString(){
        return "id_tarea: "+this.getID()+" -> 'T"+this.getID()+"';\nIndex/posicion en la lista de referencia ordenada: "+this.getValue()+";\n";
    }


}
