package tpe;

import tpe.controllers.TaskController;
import tpe.schemes.Task;
import tpe.utils.CSVReader;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Services {
    private TaskController taskController;
    private CSVReader reader;
    /*
     * Expresar la complejidad temporal del constructor.
     */
    public Services(String pathProcesadores, String pathTareas) {
        this.reader = new CSVReader();
        this.reader.readProcessors(pathProcesadores);
        this.reader.readTasks(pathTareas);
        this.taskController= new TaskController();

        //cuando se instancia un CSVReader, el CSVReader guarda las tareas en una lista de referencia para los arboles de busqueda Ó los guarda en los mismos arboles a los datos
    }

    /*
     * Expresar la complejidad temporal del servicio 1.
     */
    public Task service1(String id) {
        Task task = this.taskController.findTask(id);

        if(task == null){
            System.out.println("No se encontraron tareas con id = "+id);
        }
        return task;
    }

    /*
     * Expresar la complejidad temporal del servicio 2.
     */
    //public List<Tarea> servicio2(boolean esCritica) {}

    /*
     * Expresar la complejidad temporal del servicio 3.
     */
   // public List<Task> servicio3(int prioridadInferior, int prioridadSuperior) {}


        ///si la lista de referencia del arbol no es estatica, cada vez que instancio un arbol tengo que andar copiando la lista de tareas, siempre, todas las veces;

}
