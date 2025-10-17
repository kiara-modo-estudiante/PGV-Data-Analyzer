package net.salesianos.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    /**
     * Escribe datos en un archivo CSV.
     * 
     * @param filePath Ruta del archivo CSV de salida.
     * @param headers  Encabezados de las columnas.
     * @param data     Datos a escribir en el archivo (lista de filas).
     */
    public void writeCSV(String filePath, String[] headers, List<String[]> data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Escribir encabezados
            writer.append(String.join(",", headers));
            writer.append("\n");

            // Escribir datos
            for (String[] row : data) {
                writer.append(String.join(",", row));
                writer.append("\n");
            }

            System.out.println("Archivo CSV generado: " + filePath);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo CSV: " + e.getMessage());
        }
    }
}