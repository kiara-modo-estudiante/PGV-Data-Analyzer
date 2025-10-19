import java.util.Scanner;

import net.salesianos.utils.ConsoleColors;
import net.salesianos.utils.FileHelper;
import net.salesianos.utils.ProcessLauncher;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ProcessLauncher processLauncher = new ProcessLauncher();

        System.out.println(ConsoleColors.PURPLE + "╔════════════════════════════════════════════════════════╗");
        System.out.println(ConsoleColors.PURPLE + "║   BIENVENIDO AL ANALIZADOR DE DATOS: STOCK Y VENTAS    ║");
        System.out.println(ConsoleColors.PURPLE + "╚════════════════════════════════════════════════════════╝"
                + ConsoleColors.RESET);

        System.out.println(
                "A continuación, se detectará:\n- Las ventas realizadas por día.\n- Las ventas realizadas por producto.\n- Los productos con stock elevado (a elegir).\n- Los productos con stock escaso (a elegir).\n");

        // Preguntar al usuario por los valores de bajo y alto stock
        System.out.print(
                "1. Introduce el valor para considerar" + ConsoleColors.CYAN + " bajo stock" + ConsoleColors.RESET
                        + " un producto: ");
        String lowStock = scanner.nextLine();

        System.out.print(
                "2. Introduce el valor para considerar" + ConsoleColors.CYAN + " alto stock" + ConsoleColors.RESET
                        + " un producto: ");
        String highStock = scanner.nextLine();

        // Crear proceso para el análisis de ventas
        ProcessBuilder salesProcessBuilder = new ProcessBuilder(
                "java",
                "-cp", "./bin", // Ruta al directorio de clases compiladas
                "net.salesianos.modules.SalesAnalyzer");

        // Crear proceso para el análisis de stock
        ProcessBuilder stockProcessBuilder = new ProcessBuilder(
                "java",
                "-cp", "./bin", // Ruta al directorio de clases compiladas
                "net.salesianos.modules.StockAnalyzer", lowStock, highStock);

        // Lanzar los procesos
        processLauncher.launchProcess("SalesAnalyzer", salesProcessBuilder);
        processLauncher.launchProcess("StockAnalyzer", stockProcessBuilder);
        String dateOfFilesCreation = FileHelper.getCurrentDateTime();

        System.out.println(ConsoleColors.PURPLE + "\n¡Todos los análisis han finalizado!" + ConsoleColors.RESET);

        // Preguntar si el usuario quiere ver los resultados por consola
        System.out.print(
                ConsoleColors.YELLOW + "\n¿Deseas ver los resultados por consola? (s/n): " + ConsoleColors.RESET);
        String userResponse = scanner.nextLine().trim().toLowerCase();

        if (userResponse.equals("s")) {
            // Mostrar resoluciones finales
            System.out.println(ConsoleColors.PURPLE + "\nResultados finales:" + ConsoleColors.RESET);
            FileHelper.printGeneratedCSVOutputs(dateOfFilesCreation);
        } else {
            System.out.println(ConsoleColors.GREEN
                    + "¡Análisis completado! Los resultados están disponibles en los archivos CSV generados."
                    + ConsoleColors.RESET);
        }

        scanner.close();
    }
}