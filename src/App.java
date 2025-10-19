import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) {

        try {
            // Crear proceso para el análisis de ventas
            ProcessBuilder salesProcessBuilder = new ProcessBuilder(
                    "java",
                    "-cp", "./bin", // Ruta al directorio de clases compiladas
                    "net.salesianos.modules.SalesAnalyzer");

            // Crear proceso para el análisis de stock
            ProcessBuilder stockProcessBuilder = new ProcessBuilder(
                    "java",
                    "-cp", "./bin", // Ruta al directorio de clases compiladas
                    "net.salesianos.modules.StockAnalyzer");

            // Iniciar ambos procesos
            Process salesProcess = salesProcessBuilder.start();
            Process stockProcess = stockProcessBuilder.start();

            // Ver mensajes generados por los subprocesos
            captureProcessOutput(salesProcess, "SalesAnalyzer");
            captureProcessOutput(stockProcess, "StockAnalyzer");

            // Esperar a que ambos procesos terminen
            salesProcess.waitFor();
            stockProcess.waitFor();

            // Comprobar el código de salida de cada proceso
            int salesExitCode = salesProcess.waitFor();
            int stockExitCode = stockProcess.waitFor();

            if (salesExitCode != 0) {
                System.err.println("El proceso SalesAnalyzer terminó con errores. Código de salida: " + salesExitCode);
            }
            if (stockExitCode != 0) {
                System.err.println("El proceso StockAnalyzer terminó con errores. Código de salida: " + stockExitCode);
            }

            System.out.println("Todos los análisis han finalizado.");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al ejecutar los procesos: " + e.getMessage());
        }
    }

    private static void captureProcessOutput(Process process, String processName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            System.out.println("Salida de " + processName + ":");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            System.out.println("Errores de " + processName + ":");
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
        }
    }
}