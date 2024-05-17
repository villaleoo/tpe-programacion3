package tpe.utils;

import tpe.filters.TreeSearchTaskID;
import tpe.schemes.Task;
import tpe.schemes.TreeSearchTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class CSVReader {
    private ArrayList<Task> tareas;


    ////podria aplicar un patron singleton para el reader
    public CSVReader() {
        this.tareas=new ArrayList<>();
    }



    public ArrayList<Task> getTasks(){
        return new ArrayList<>(this.tareas);
    }

    public void readTasks(String taskPath) {

        // Obtengo una lista con las lineas del archivo
        // lines.get(0) tiene la primer linea del archivo
        // lines.get(1) tiene la segunda linea del archivo... y así
        ArrayList<String[]> lines = this.readContent(taskPath);

        for (String[] line: lines) {
            // Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
            String id = line[0].trim();
            String nombre = line[1].trim();
            Integer tiempo = Integer.parseInt(line[2].trim());
            Boolean critica = Boolean.parseBoolean(line[3].trim());
            Integer prioridad = Integer.parseInt(line[4].trim());
            // Aca instanciar lo que necesiten en base a los datos leidos
            Task nuevaTarea= new Task(id,nombre,tiempo,critica,prioridad);
            this.tareas.add(nuevaTarea);
        }

        ///cuando ejecuto el metodo de leer tareas, actualizo la lista de referencia de los arboles de busqueda ordenados. La lista del arbol es estatica para
        ///que sea siempre la misma y no se pierda al instanciar un nuevo arbol
        TreeSearchTaskID.setListaRef(getTasks());

    }

    public void readProcessors(String processorPath) {

        // Obtengo una lista con las lineas del archivo
        // lines.get(0) tiene la primer linea del archivo
        // lines.get(1) tiene la segunda linea del archivo... y así
        ArrayList<String[]> lines = this.readContent(processorPath);

        for (String[] line: lines) {
            // Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
            String id = line[0].trim();
            String codigo = line[1].trim();
            Boolean refrigerado = Boolean.parseBoolean(line[2].trim());
            Integer anio = Integer.parseInt(line[3].trim());
            // Aca instanciar lo que necesiten en base a los datos leidos
        }

    }

    private ArrayList<String[]> readContent(String path) {
        ArrayList<String[]> lines = new ArrayList<String[]>();

        File file = new File(path);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                lines.add(line.split(";"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        }

        return lines;
    }

}