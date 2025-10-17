import net.salesianos.modules.StockAnalyzer;

public class App {
    public static void main(String[] args) {
        final String PRODUCTS_CSV_PATH = "./src/data/input/products.csv";
        final String LOW_STOCK_CSV_PATH = "./src/data/output/low_stock_report.csv";

        StockAnalyzer stockAnalyzer = new StockAnalyzer();
        stockAnalyzer.analyzeStock(PRODUCTS_CSV_PATH, LOW_STOCK_CSV_PATH);
    }
}