import java.io.IOException;

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

            // Esperar a que ambos procesos terminen
            salesProcess.waitFor();
            stockProcess.waitFor();

            System.out.println("Todos los análisis han finalizado.");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al ejecutar los procesos: " + e.getMessage());
        }
    }
}