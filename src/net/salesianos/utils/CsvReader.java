package net.salesianos.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // Guardar datos en strings
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }

            // Mostrar datos en consola
            for (String[] row : data) {
                for (String value : row) {
                    System.out.print(value + " ");
                }
                System.out.println();

            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return data;
    }
}