package net.salesianos.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessLauncher {

    /**
     * Lanza y gestiona una lista de procesos proporcionados mediante
     * ProcessBuilder de manera concurrente.
     * 
     * @param processBuilders Lista de objetos ProcessBuilder que representan los
     *                        procesos a ejecutar.
     * 
     * @throws IOException          Si ocurre un error al iniciar un proceso.
     * @throws InterruptedException Si el hilo principal es interrumpido mientras
     *                              espera que un proceso termine.
     */
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

    /**
     * Captura y muestra la salida estándar y de error de un proceso en ejecución.
     *
     * @param process     El proceso del cual se capturará la salida.
     * @param processName El nombre del proceso, utilizado para identificar la
     *                    salida.
     * @throws IOException Si ocurre un error al leer las salidas del proceso.
     */
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
    /**
     * Obtiene el nombre del proceso a partir de un ProcessBuilder.
     * Busca en la lista de comandos si contiene "SalesAnalyzer" o "StockAnalyzer".
     * 
     * @param processBuilder El ProcessBuilder que contiene la configuración del
     *                       proceso.
     * @return El nombre del proceso si se encuentra, o "Proceso desconocido" si no
     *         coincide.
     */
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
