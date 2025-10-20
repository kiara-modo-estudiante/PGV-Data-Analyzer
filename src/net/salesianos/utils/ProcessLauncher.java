package net.salesianos.utils;

import java.io.*;
import java.util.*;

public class ProcessLauncher {

    public void launchProcesses(List<ProcessBuilder> processBuilders) {
        List<Process> processes = new ArrayList<>();

        try {
            // Lanzar todos los procesos (concurrente)
            for (ProcessBuilder builder : processBuilders) {
                Process process = builder.start();
                processes.add(process);
            }

            // Capturar la salida de cada proceso
            for (int i = 0; i < processes.size(); i++) {
                Process process = processes.get(i);
                String processName = getProcessName(processBuilders.get(i)); // Obtener el nombre del proceso
                try {
                    captureProcessOutput(process, processName);
                } catch (IOException e) {
                    System.err.println(ConsoleColors.RED + "Error capturando salida de proceso: " + e.getMessage()
                            + ConsoleColors.RESET);
                }
            }

            // Esperar a que todos terminen
            for (Process process : processes) {
                process.waitFor();
            }

        } catch (IOException | InterruptedException e) {
            System.err
                    .println(ConsoleColors.RED + "Error al ejecutar procesos: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    private static void captureProcessOutput(Process process, String processName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            System.out.println("\n⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯");
            System.out
                    .println("\n\t\t" + ConsoleColors.BLUE + ConsoleColors.BOLD + ConsoleColors.UNDERLINE + "Salida de "
                            + processName + ":" + ConsoleColors.RESET + "\n");

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            System.out.print(ConsoleColors.YELLOW + "Errores de " + processName + ": " + ConsoleColors.RESET);
            boolean hasErrors = false;

            while ((line = errorReader.readLine()) != null) {
                hasErrors = true;
                System.err.println(line);
            }
            if (!hasErrors) {
                System.out.println("Ninguno.");

                System.out.println(
                        ConsoleColors.GREEN_BACKGROUND + "¡Este proceso ha terminado con éxito!" + ConsoleColors.RESET);
            } else {
                System.out.println(
                        ConsoleColors.RED_BACKGROUND + "¡Oops! Algo anda mal..." + ConsoleColors.RESET);
            }
        }
    }

    // Método para obtener el nombre del proceso a partir del comando
    private String getProcessName(ProcessBuilder processBuilder) {
        List<String> command = processBuilder.command();
        for (String part : command) {
            if (part.contains("SalesAnalyzer") || part.contains("StockAnalyzer")) {
                return part; // Retorna el nombre del proceso
            }
        }
        return "Proceso desconocido";
    }

}
