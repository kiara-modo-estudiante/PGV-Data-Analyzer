import java.util.Scanner;

import net.salesianos.utils.ConsoleColors;
import net.salesianos.utils.FileHelper;
import net.salesianos.utils.ProcessLauncher;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ProcessLauncher processLauncher = new ProcessLauncher();

        // Crear proceso para el análisis de ventas
        ProcessBuilder salesProcessBuilder = new ProcessBuilder(
                "java",
                "-cp", "./bin", // Ruta al directorio de clases compiladas
                "net.salesianos.modules.SalesAnalyzer");

        // Preguntar al usuario por los valores de bajo y alto stock
        System.out.print("Introduce el valor para considerar bajo stock: ");
        String lowStock = scanner.nextLine();

        System.out.print("Introduce el valor para considerar alto stock: ");
        String highStock = scanner.nextLine();

        // Crear proceso para el análisis de stock
        ProcessBuilder stockProcessBuilder = new ProcessBuilder(
                "java",
                "-cp", "./bin", // Ruta al directorio de clases compiladas
                "net.salesianos.modules.StockAnalyzer", lowStock, highStock);

        // Lanzar los procesos
        processLauncher.launchProcess("SalesAnalyzer", salesProcessBuilder);
        processLauncher.launchProcess("StockAnalyzer", stockProcessBuilder);

        System.out.println(ConsoleColors.PURPLE + "\n¡Todos los análisis han finalizado!" + ConsoleColors.RESET);

        // Mostrar resoluciones finales
        System.out.println(ConsoleColors.PURPLE + "\nResultados finales:" + ConsoleColors.RESET);

        FileHelper.printAllCSVOutputs();

        scanner.close();
    }
}