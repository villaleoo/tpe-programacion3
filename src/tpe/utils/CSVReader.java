package tpe.utils;

import tpe.filters.SearchByCritic;
import tpe.filters.SearchById;
import tpe.filters.SearchGroupPriority;
import tpe.structures.Processor;
import tpe.structures.Task;
import tpe.structures.TreeTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class CSVReader {
    private final ArrayList<Task> tasks;
    private final ArrayList<Processor> processors;

    public CSVReader() {
        this.tasks = new ArrayList<>();
        this.processors = new ArrayList<>();
    }


    public void readTasks(String taskPath) {

        // Obtengo una lista con las lineas del archivo
        // lines.get(0) tiene la primer linea del archivo
        // lines.get(1) tiene la segunda linea del archivo... y así
        ArrayList<String[]> lines = this.readContent(taskPath);

        for (String[] line : lines) {
            // Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
            String id = line[0].trim();
            String nombre = line[1].trim();
            Integer tiempo = Integer.parseInt(line[2].trim());
            Boolean critica = Boolean.parseBoolean(line[3].trim());
            Integer prioridad = Integer.parseInt(line[4].trim());
            // Aca instanciar lo que necesiten en base a los datos leidos

            Task newTask = new Task(id, nombre, tiempo, critica, prioridad);
            SearchById.addTask(newTask, id);
            this.tasks.add(newTask);
            if (critica)
                SearchByCritic.addCriticTask(newTask);
            else SearchByCritic.addNotCriticTask(newTask);
        }

        TreeTask.setRefList(this.tasks);  //le envia las tareas al arbol binario del servicio3
    }


    public void readProcessors(String processorPath) {

        // Obtengo una lista con las lineas del archivo
        // lines.get(0) tiene la primer linea del archivo
        // lines.get(1) tiene la segunda linea del archivo... y así
        ArrayList<String[]> lines = this.readContent(processorPath);

        for (String[] line : lines) {
            // Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
            String id = line[0].trim();
            String codigo = line[1].trim();
            Boolean refrigerado = Boolean.parseBoolean(line[2].trim());
            Integer anio = Integer.parseInt(line[3].trim());
            // Aca instanciar lo que necesiten en base a los datos leidos
            Processor p = new Processor(id, codigo, refrigerado, anio);
            this.processors.add(p);
        }

    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public ArrayList<Processor> getProcessors() {
        return this.processors;
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