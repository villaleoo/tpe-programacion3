package tpe;

import tpe.schemes.Task;

public class Main {

    public static void main(String[] args) {
        Services servicios = new Services("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");
        Task resultado = servicios.service1("t22");

        if(resultado != null){
            System.out.println(resultado);
        }
    }
}
