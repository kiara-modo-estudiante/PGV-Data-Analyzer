package net.salesianos.modules;

import net.salesianos.utils.CsvReader;
import net.salesianos.utils.CsvWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StockAnalyzer {

    public void analyzeStock(String inputFilePath, String outputFilePath) {
        CsvReader reader = new CsvReader();
        CsvWriter writer = new CsvWriter();

        // Leer datos del archivo CSV
        List<String[]> products = reader.readCSV(inputFilePath);

        // Filtrar productos con bajo stock
        List<String[]> lowStockProducts = new ArrayList<>();
        boolean isFirstRow = true; // Bandera para ignorar la primera fila
        for (String[] product : products) {
            if (isFirstRow) {
                isFirstRow = false; // Ignorar encabezados
                continue;
            }
            try {
                int stock = Integer.parseInt(product[4]); // Columna "Cantidad en stock"
                if (stock < 50) { // Umbral de bajo stock
                    lowStockProducts.add(product);
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Error al procesar el producto: " + e.getMessage());
            }
        }

        // Crear la carpeta de salida si no existe
        File outputDir = new File(outputFilePath).getParentFile();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Escribir productos con bajo stock en un nuevo archivo CSV
        String[] headers = { "Id", "Nombre", "Categoría", "Precio unidad", "Cantidad en stock", "Almacén" };
        writer.writeCSV(outputFilePath, headers, lowStockProducts);
    }
}