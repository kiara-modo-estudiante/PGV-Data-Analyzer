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

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return data;
    }

    /**
     * Lee un archivo CSV desde la ruta especificada y lo imprime en consola.
     *
     * @param filePath La ruta del archivo CSV que se desea leer e imprimir.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static void printCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // Guardar datos en strings
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }

            // Calcular el ancho máximo de cada columna
            int[] columnWidths = new int[data.get(0).length];
            for (String[] row : data) {
                for (int i = 0; i < row.length; i++) {
                    columnWidths[i] = Math.max(columnWidths[i], row[i].length());
                }
            }

            // Imprimir datos en formato tabular con printf
            for (int rowIndex = 0; rowIndex < data.size(); rowIndex++) {
                String[] row = data.get(rowIndex);
                for (int i = 0; i < row.length; i++) {
                    if (rowIndex == 0) {
                        // Imprimir la primera fila en negrita
                        System.out.printf(ConsoleColors.BOLD + "%-" + (columnWidths[i] + 3) + "s" + ConsoleColors.RESET,
                                row[i]);
                    } else {
                        System.out.printf("%-" + (columnWidths[i] + 3) + "s", row[i]);
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Imprime el contenido de los archivos CSV generados en una fecha especifica
     * (por el nombre que tienen)
     *
     * @param dateOfFile fecha de creación.
     */
    public static void printGeneratedCSVOutputs(String dateOfFile) {
        File outputDir = new File("./src/data/output");
        if (outputDir.exists() && outputDir.isDirectory()) {
            File[] files = outputDir.listFiles((dir, name) -> name.contains(dateOfFile) && name.endsWith(".csv"));
            if (files != null) {
                for (File file : files) {
                    System.out.println("⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯");
                    System.out.println(
                            ConsoleColors.CYAN + "Contenido del archivo: " + file.getName() + ConsoleColors.RESET);
                    printCSV(file.getPath());
                }
            } else {
                System.out.println("No se encontraron archivos CSV creados ahora mismo.");
            }
        } else {
            System.out.println("El directorio 'outputs' no existe.");
        }
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

            System.out.println(ConsoleColors.CYAN + "Archivo CSV generado: " + ConsoleColors.RESET + filePath);
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

        int dotIndex = filePath.lastIndexOf(".");
        if (dotIndex == -1) {
            return filePath + "_" + getCurrentDateTime(); // Si no hay extensión, solo agrega la fecha y hora
        }
        String name = filePath.substring(0, dotIndex);
        String extension = filePath.substring(dotIndex);
        return name + "_" + getCurrentDateTime() + extension; // Agrega la fecha y hora antes de la extensión
    }

    /**
     * Devuelve la fecha y hora actual en el formato "yyyyMMddHHmm".
     *
     * @return La fecha y hora actual como cadena.
     */
    public static String getCurrentDateTime() {
        return LocalDate.now().atTime(java.time.LocalTime.now())
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
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
