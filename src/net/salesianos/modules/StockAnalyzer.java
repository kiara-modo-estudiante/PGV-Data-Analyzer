package net.salesianos.modules;

import net.salesianos.utils.FileHelper;

import java.util.ArrayList;
import java.util.List;

public class StockAnalyzer {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Uso: java StockAnalyzer <inputFilePath> <lowStockOutputPath> <highStockOutputPath>");
            System.exit(1);
        }

        String inputFilePath = args[0];
        String lowStockOutputPath = args[1];
        String highStockOutputPath = args[2];

        StockAnalyzer analyzer = new StockAnalyzer();
        analyzer.analyzeStock(inputFilePath, lowStockOutputPath, highStockOutputPath);
    }

    public void analyzeStock(String inputFilePath, String lowStockOutputPath, String highStockOutputPath) {
        // Incluir la fecha en ficheros de salida
        lowStockOutputPath = FileHelper.appendDateToFileName(lowStockOutputPath);
        highStockOutputPath = FileHelper.appendDateToFileName(highStockOutputPath);

        // Leer datos del archivo CSV
        List<String[]> products = FileHelper.readCSV(inputFilePath);

        // Filtrar productos con bajo y alto stock
        List<String[]> lowStockProducts = filterProductsByStock(products, 0, 50);
        List<String[]> highStockProducts = filterProductsByStock(products, 500, Integer.MAX_VALUE);

        // Crear las carpetas de salida si no existen
        FileHelper.createOutputDirectory(lowStockOutputPath);
        FileHelper.createOutputDirectory(highStockOutputPath);

        // Escribir los resultados en archivos CSV
        String[] headers = { "Id", "Nombre", "Categoría", "Precio unidad", "Cantidad en stock", "Almacén" };
        FileHelper.writeCSV(lowStockOutputPath, headers, lowStockProducts);
        FileHelper.writeCSV(highStockOutputPath, headers, highStockProducts);
    }

    private List<String[]> filterProductsByStock(List<String[]> products, int minStock, int maxStock) {
        List<String[]> filteredProducts = new ArrayList<>();
        boolean isFirstRow = true; // Ignorar encabezados
        for (String[] product : products) {
            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }
            try {
                int stock = Integer.parseInt(product[4]); // Columna "Cantidad en stock"
                if (stock >= minStock && stock < maxStock) {
                    filteredProducts.add(product);
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Error al procesar el producto: " + e.getMessage());
            }
        }
        return filteredProducts;
    }
}