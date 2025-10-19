package net.salesianos.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessLauncher {

    public void launchProcess(String processName, ProcessBuilder processBuilder) {
        try {
            // Iniciar el proceso
            Process process = processBuilder.start();

            // Capturar la salida del proceso
            captureProcessOutput(process, processName);

            // Esperar a que el proceso termine
            int exitCode = process.waitFor();

            // Verificar el código de salida
            if (exitCode != 0) {
                System.err.println(ConsoleColors.RED + "El proceso " + processName
                        + " terminó con errores. Código de salida: " + exitCode + ConsoleColors.RESET);
            } else {
                System.out.println(ConsoleColors.GREEN + "\nEl proceso " + processName + " terminó correctamente."
                        + ConsoleColors.RESET);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(ConsoleColors.RED + "Error al ejecutar el proceso " + processName + ": " + e.getMessage()
                    + ConsoleColors.RESET);
            e.printStackTrace();
        }
    }

    private static void captureProcessOutput(Process process, String processName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            System.out.println("⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯");
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
            }
        }
    }
}