import net.salesianos.utils.ConsoleColors;
import net.salesianos.utils.ProcessLauncher;

public class App {
    public static void main(String[] args) {
        ProcessLauncher processLauncher = new ProcessLauncher();

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

        // Lanzar los procesos
        processLauncher.launchProcess("SalesAnalyzer", salesProcessBuilder);
        processLauncher.launchProcess("StockAnalyzer", stockProcessBuilder);

        System.out.println(ConsoleColors.PURPLE + "\n¡Todos los análisis han finalizado!" + ConsoleColors.RESET);
    }
}