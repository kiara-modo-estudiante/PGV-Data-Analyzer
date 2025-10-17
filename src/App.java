import net.salesianos.utils.CsvReader;

public class App {
    public static void main(String[] args) throws Exception {

        final String PRODUCTS_CSV_PATH = "./src/data/input/products.csv";
        final String SALES_CSV_PATH = "./src/data/input/sales.csv";

        CsvReader fileReader = new CsvReader();
        fileReader.readCSV(PRODUCTS_CSV_PATH);

    }
}
