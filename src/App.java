import java.io.IOException;

public class App {
    public static void main(String[] args) {

        // RUTAS DE STOCK
        final String PRODUCTS_CSV_PATH = "./src/data/input/products.csv";
        final String LOW_STOCK_CSV_PATH = "./src/data/output/low_stock_report.csv";
        final String HIGH_STOCK_CSV_PATH = "./src/data/output/high_stock_report.csv";

        // RUTAS DE VENTAS
        final String SALES_CSV_PATH = "./src/data/input/sales.csv";
        final String PRODUCT_REPORT_PATH = "./src/data/output/product_sales_report.csv";
        final String DATE_REPORT_PATH = "./src/data/output/sales_by_date_report.csv";

        try {
            // Crear proceso para el análisis de ventas
            ProcessBuilder salesProcessBuilder = new ProcessBuilder(
                    "java",
                    "-cp", "./bin", // Ruta al directorio de clases compiladas
                    "net.salesianos.modules.SalesAnalyzer",
                    SALES_CSV_PATH,
                    PRODUCT_REPORT_PATH,
                    DATE_REPORT_PATH);

            // Crear proceso para el análisis de stock
            ProcessBuilder stockProcessBuilder = new ProcessBuilder(
                    "java",
                    "-cp", "./bin", // Ruta al directorio de clases compiladas
                    "net.salesianos.modules.StockAnalyzer",
                    PRODUCTS_CSV_PATH,
                    LOW_STOCK_CSV_PATH,
                    HIGH_STOCK_CSV_PATH);

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