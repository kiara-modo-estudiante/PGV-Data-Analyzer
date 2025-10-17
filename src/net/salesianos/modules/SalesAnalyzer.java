package net.salesianos.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.salesianos.utils.FileHelper;

public class SalesAnalyzer {

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

    /**
     * Genera un informe de productos basado en la cantidad de ventas y los datos de
     * ingresos.
     *
     * @param productSalesCount Un mapa que contiene la cantidad de ventas para cada
     *                          producto.
     * @param productRevenue    Un mapa que contiene los ingresos para cada
     *                          producto.
     * @return Una lista de arreglos de cadenas, donde cada arreglo representa una
     *         fila en el informe.
     * 
     */
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

    /**
     * Genera un informe de ventas por fecha a partir de un mapa que contiene las
     * ventas por fecha.
     *
     * @param salesByDate Un mapa donde las claves son fechas (String) y los valores
     *                    son las ventas (Integer) asociadas a esas fechas.
     * @return Una lista de arreglos de cadenas (String[]), donde cada arreglo
     *         contiene dos elementos:
     *         el primero es la fecha y el segundo es el número de ventas en formato
     *         de cadena.
     */
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