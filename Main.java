package tpe;


import tpe.services.Services;
import tpe.structures.Task;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Services services = new Services("datasets/Procesadores.csv", "datasets/Tareas.csv");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Por favor, ingrese el valor de la tarea a buscar: ");
        String valor = scanner.nextLine();
        System.out.println("RESULTADO SERVICIO 1: ");
        Task getTaskById = services.service1(valor);
        if (getTaskById != null)
            System.out.println(getTaskById);

        System.out.println("SERVICIO 2");

        System.out.print("Ingrese 1 para obtener las tareas " +
                "criticas o 0 para obtener las tareas no criticas: ");

        int critic = scanner.nextInt();
        List<Task> taskByCritic = services.servicio2(critic == 1);

        System.out.println("RESULTADO SERVICIO 2:\n");
        System.out.println(taskByCritic);

        System.out.println("RESULTADO SERVICIO 3:\n");

        System.out.print("Ingrese prioridad inferior: ");
        int priorityInf = scanner.nextInt();
        System.out.print("Ingrese prioridad superior: ");
        int prioritySup = scanner.nextInt();
        List<Task> getTasksByPriorities = services.servicio3(priorityInf, prioritySup);
        if (getTasksByPriorities != null && !getTasksByPriorities.isEmpty())
            System.out.println(getTasksByPriorities);
    }
}
