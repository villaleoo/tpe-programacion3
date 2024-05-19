package tpe.schemes;

public interface Node<T> {

    T getValue();
    int getID();
    Node getRight();
    Node getLeft();
    void setLeft (T leftNode);
    void setRight (T rightNode);

}
