package tpe;


import tpe.services.Backtracking;
import tpe.services.Greedy;
import tpe.services.Services;
import tpe.structures.Task;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);



        Services services = new Services("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");
        Backtracking backtracking = new Backtracking("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");
        System.out.println("--------------- Primera parte --------------------\n");
        System.out.println("---------- SERVICIO 1 ----------");
        System.out.print("Por favor, ingrese el valor de la tarea a buscar: ");
        String valor = scanner.nextLine();
        System.out.println("RESULTADO SERVICIO 1: ");
        Task getTaskById = services.service1(valor);
        if (getTaskById != null)
            System.out.println(getTaskById);

        System.out.println("--------- SERVICIO 2 -----------");

        System.out.print("Ingrese 1 para obtener las tareas " +
                "criticas o 0 para obtener las tareas no criticas: ");

        int critic = scanner.nextInt();
        List<Task> taskByCritic = services.servicio2(critic == 1);

        System.out.println("RESULTADO SERVICIO 2:\n");
        System.out.println(taskByCritic);


        System.out.println("--------- SERVICIO 3 -----------");
        System.out.print("Ingrese prioridad inferior: ");
        int priorityInf = scanner.nextInt();
        System.out.print("Ingrese prioridad superior: ");
        int prioritySup = scanner.nextInt();
        System.out.println("RESULTADO SERVICIO 3:\n");
        List<Task> getTasksByPriorities = services.servicio3(priorityInf, prioritySup);
        if (getTasksByPriorities != null && !getTasksByPriorities.isEmpty())
            System.out.println(getTasksByPriorities);

        System.out.println("\n--------------- Segunda parte --------------------\n");
        System.out.println("--------------- GREEDY ---------------------------");
        System.out.print("Por favor, ingrese el valor X para minimizar el tiempo de ejecución: ");
        float x = scanner.nextFloat();
        Greedy g = new Greedy("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");
        g.printAssigment(x);

        System.out.println("\n ----------- BACKTRACKING ----------");

        backtracking.getAssignments(x);

        scanner.close();


    }
}
