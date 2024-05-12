package tpe;

import tpe.filtros.ArbolBinarioBusqueda;
import tpe.utils.CSVReader;

import java.util.List;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

    private CSVReader reader;
    /*
     * Expresar la complejidad temporal del constructor.
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        this.reader = new CSVReader();
        this.reader.readProcessors(pathProcesadores);
        this.reader.readTasks(pathTareas);
    }

    /*
     * Expresar la complejidad temporal del servicio 1.
     */
    //public Tarea servicio1(String ID) {	}

    /*
     * Expresar la complejidad temporal del servicio 2.
     */
    //public List<Tarea> servicio2(boolean esCritica) {}

    /*
     * Expresar la complejidad temporal del servicio 3.
     */
    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        ArbolBinarioBusqueda arbol= new ArbolBinarioBusqueda();
        ///si la lista de referencia del arbol no es estatica, cada vez que instancio un arbol tengo que andar copiando la lista de tareas, siempre, todas las veces;
        return null;
    }

}
