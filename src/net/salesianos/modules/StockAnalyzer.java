package net.salesianos.modules;

import net.salesianos.utils.FileHelper;

import java.util.ArrayList;
import java.util.List;

public class StockAnalyzer {

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

    /**
     * Filtra una lista de productos según su cantidad en stock.
     *
     * @param products Una lista de datos de productos.
     * @param minStock La cantidad mínima en stock (inclusive).
     * @param maxStock La cantidad máxima en stock (exclusiva).
     * @return Una lista de productos con cantidad en stock dentro del rango.
     * 
     * @throws NumberFormatException          Si la cantidad en stock no se puede
     *                                        analizar como un entero.
     * 
     * @throws ArrayIndexOutOfBoundsException Si el array del producto no tiene
     *                                        suficientes columnas.
     */
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