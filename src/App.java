import net.salesianos.modules.SalesAnalyzer;
import net.salesianos.modules.StockAnalyzer;

public class App {
    public static void main(String[] args) {

        // RUTAS DE STOCK
        // final String PRODUCTS_CSV_PATH = "./src/data/input/products.csv";
        // final String LOW_STOCK_CSV_PATH = "./src/data/output/low_stock_report.csv";
        // final String HIGH_STOCK_CSV_PATH = "./src/data/output/high_stock_report.csv";

        // RUTAS DE VENTAS
        final String SALES_CSV_PATH = "./src/data/input/sales.csv";
        final String PRODUCT_REPORT_PATH = "./src/data/output/product_sales_report.csv";
        final String DATE_REPORT_PATH = "./src/data/output/sales_by_date_report.csv";

        SalesAnalyzer salesAnalyzer = new SalesAnalyzer();
        salesAnalyzer.analyzeSales(SALES_CSV_PATH, PRODUCT_REPORT_PATH, DATE_REPORT_PATH);

        // StockAnalyzer stockAnalyzer = new StockAnalyzer();
        // stockAnalyzer.analyzeStock(PRODUCTS_CSV_PATH, LOW_STOCK_CSV_PATH,
        // HIGH_STOCK_CSV_PATH);
    }
}