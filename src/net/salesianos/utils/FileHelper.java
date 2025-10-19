package net.salesianos.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    /**
     * Lee un archivo CSV desde la ruta especificada y devuelve su contenido como
     * una lista de arreglos de cadenas.
     *
     * @param filePath La ruta del archivo CSV a leer.
     * @return Una lista de arreglos de cadenas.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // Guardar datos en strings
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }

            // // Mostrar datos en consola
            // for (String[] row : data) {
            // for (String value : row) {
            // System.out.print(value + " ");
            // }
            // System.out.println();

            // }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return data;
    }

    /**
     * Escribe datos en un archivo CSV.
     * 
     * @param filePath Ruta del archivo CSV de salida.
     * @param headers  Encabezados de las columnas.
     * @param data     Datos a escribir en el archivo (lista de filas).
     */
    public static void writeCSV(String filePath, String[] headers, List<String[]> data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Escribir encabezados
            writer.append(String.join(",", headers));
            writer.append("\n");

            // Escribir datos
            for (String[] row : data) {
                writer.append(String.join(",", row));
                writer.append("\n");
            }

            System.out.println(ConsoleColors.GREEN + "Archivo CSV generado: " + ConsoleColors.RESET + filePath);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo CSV: " + e.getMessage());
        }
    }

    /**
     * Agrega una fecha al final del nombre del archivo, antes de la extensión.
     *
     * @param filePath Ruta del archivo original.
     * @param date     Fecha a agregar al nombre del archivo.
     * @return Ruta del archivo con la fecha agregada.
     */
    public static String appendDateToFileName(String filePath) {
        // Obtener la fecha y hora actual y formatearlas
        String currentDateTime = LocalDate.now().atTime(java.time.LocalTime.now())
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        int dotIndex = filePath.lastIndexOf(".");
        if (dotIndex == -1) {
            return filePath + "_" + currentDateTime; // Si no hay extensión, solo agrega la fecha y hora
        }
        String name = filePath.substring(0, dotIndex);
        String extension = filePath.substring(dotIndex);
        return name + "_" + currentDateTime + extension; // Agrega la fecha y hora antes de la extensión
    }

    /**
     * Crea el directorio de salida si no existe.
     *
     * @param filePath Ruta del archivo para el cual se debe crear el directorio.
     */
    public static void createOutputDirectory(String filePath) {
        File outputDir = new File(filePath).getParentFile();
        if (outputDir != null && !outputDir.exists()) {
            outputDir.mkdirs();
        }
    }
}
