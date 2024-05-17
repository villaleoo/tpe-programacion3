package tpe.schemes;

public interface Node<T> {

    T getRight();
    T getLeft();
    void setLeft (T leftNode);
    void setRight (T rightNode);
}
