package net.salesianos.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.salesianos.utils.FileHelper;

public class SalesAnalyzer {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Uso: java SalesAnalyzer <inputFilePath> <productReportPath> <dateReportPath>");
            System.exit(1);
        }

        String inputFilePath = args[0];
        String productReportPath = args[1];
        String dateReportPath = args[2];

        SalesAnalyzer analyzer = new SalesAnalyzer();
        analyzer.analyzeSales(inputFilePath, productReportPath, dateReportPath);
    }

    public void analyzeSales(String inputFilePath, String productReportPath, String dateReportPath) {
        // Leer datos del archivo CSV
        List<String[]> salesData = FileHelper.readCSV(inputFilePath);

        // Ignorar encabezados
        boolean isFirstRow = true;

        // Mapas para almacenar estadísticas
        Map<String, Integer> productSalesCount = new HashMap<>();
        Map<String, Double> productRevenue = new HashMap<>();
        Map<String, Integer> salesByDate = new TreeMap<>();

        for (String[] sale : salesData) {
            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }
            try {
                String productId = sale[1]; // Columna "IdProducto"
                int quantity = Integer.parseInt(sale[2]); // Columna "Cantidad"
                String date = sale[3]; // Columna "Fecha"
                double totalPrice = Double.parseDouble(sale[4]); // Columna "PrecioTotal"

                // Contar ventas por producto
                productSalesCount.put(productId, productSalesCount.getOrDefault(productId, 0) + quantity);

                // Calcular ingresos por producto
                productRevenue.put(productId, productRevenue.getOrDefault(productId, 0.0) + totalPrice);

                // Contar ventas por fecha
                salesByDate.put(date, salesByDate.getOrDefault(date, 0) + quantity);

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Error al procesar la venta: " + e.getMessage());
            }
        }

        // Generar informes separados
        List<String[]> productReportData = generateProductReport(productSalesCount, productRevenue);
        List<String[]> dateReportData = generateDateReport(salesByDate);

        // Crear las carpetas de salida si no existen
        FileHelper.createOutputDirectory(productReportPath);
        FileHelper.createOutputDirectory(dateReportPath);

        // Escribir los informes en archivos CSV separados
        String[] productHeaders = { "IdProducto", "CantidadVendida", "IngresosTotales" };
        FileHelper.writeCSV(productReportPath, productHeaders, productReportData);

        String[] dateHeaders = { "Fecha", "VentasPorFecha" };
        FileHelper.writeCSV(dateReportPath, dateHeaders, dateReportData);
    }

    private List<String[]> generateProductReport(Map<String, Integer> productSalesCount,
            Map<String, Double> productRevenue) {
        List<String[]> report = new ArrayList<>();
        for (String productId : productSalesCount.keySet()) {
            String[] row = new String[3];
            row[0] = productId;
            row[1] = String.valueOf(productSalesCount.get(productId));
            row[2] = String.format("%.2f", productRevenue.get(productId));
            report.add(row);
        }
        return report;
    }

    private List<String[]> generateDateReport(Map<String, Integer> salesByDate) {
        List<String[]> report = new ArrayList<>();
        for (String date : salesByDate.keySet()) {
            String[] row = new String[2];
            row[0] = date;
            row[1] = String.valueOf(salesByDate.get(date));
            report.add(row);
        }
        return report;
    }
}