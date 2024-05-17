package tpe.schemes;

import java.util.ArrayList;

//esto esta implementado en base a tareas porque en este caso solo se utilizaran arboles para tareas, si hubiera mas recursos con estos servicios,
// se podria darle mas abstraccion a los metodos
public interface TreeSearchTask {

     boolean hasElement(int taskId);
     boolean isEmpty();
     void insert(Task task);
     int getHeight();
     void printPosOrder();
     void printPreOrder();
     void printInOrder();



}
