package tpe.schemes;

public class SimpleNode implements Node{
    private int numericID;
    private int indexRef;
    private SimpleNode left,right;

    public SimpleNode(int numericID, int indexRef){
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
    public SimpleNode getRight() {
        return this.right;
    }

    @Override
    public SimpleNode getLeft() {
        return this.left;
    }

    @Override
    public void setLeft(Object leftNode) {
        this.left = (SimpleNode) leftNode;
    }

    @Override
    public void setRight(Object rightNode) {
        this.right= (SimpleNode) rightNode;
    }


    public String toString(){
        return "id_tarea: "+this.getID()+" -> 'T"+this.getID()+"';\nIndex/posicion en la lista de referencia ordenada: "+this.getValue()+";\n";
    }


}
