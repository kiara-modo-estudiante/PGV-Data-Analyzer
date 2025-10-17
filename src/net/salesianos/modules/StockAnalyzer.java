package net.salesianos.modules;

import net.salesianos.utils.FileHelper;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StockAnalyzer {

    public void analyzeStock(String inputFilePath, String lowStockOutputPath, String highStockOutputPath) {
        FileHelper reader = new FileHelper();
        FileHelper writer = new FileHelper();

        // Obtener la fecha actual y formatearla
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Modificar los nombres de los archivos de salida para incluir la fecha
        lowStockOutputPath = FileHelper.appendDateToFileName(lowStockOutputPath, currentDate);
        highStockOutputPath = FileHelper.appendDateToFileName(highStockOutputPath, currentDate);

        // Leer datos del archivo CSV
        List<String[]> products = reader.readCSV(inputFilePath);

        // Filtrar productos con bajo y alto stock
        List<String[]> lowStockProducts = new ArrayList<>();
        List<String[]> highStockProducts = new ArrayList<>();
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
                } else if (stock > 500) { // Umbral de alto stock
                    highStockProducts.add(product);
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Error al procesar el producto: " + e.getMessage());
            }
        }

        // Crear la carpeta de salida si no existe
        createOutputDirectory(lowStockOutputPath);
        createOutputDirectory(highStockOutputPath);

        // Escribir productos con bajo stock en un nuevo archivo CSV
        String[] headers = { "Id", "Nombre", "Categoría", "Precio unidad", "Cantidad en stock", "Almacén" };
        writer.writeCSV(lowStockOutputPath, headers, lowStockProducts);

        // Escribir productos con alto stock en un nuevo archivo CSV
        writer.writeCSV(highStockOutputPath, headers, highStockProducts);
    }

    private void createOutputDirectory(String filePath) {
        File outputDir = new File(filePath).getParentFile();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
    }
}