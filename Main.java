package tpe;



import tpe.schemes.Task;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Services servicios = new Services("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");
        List<Task> resultado = servicios.servicio3(13,71);


        System.out.println("RESULTADO SERVICIO 3:\n");

        if(resultado != null){
            System.out.println(resultado);
        }



    }
}
