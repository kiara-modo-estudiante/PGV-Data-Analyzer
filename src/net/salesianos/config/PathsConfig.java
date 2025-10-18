package net.salesianos.config;

public class PathsConfig {

    // Rutas de entrada
    public static final String PRODUCTS_CSV_PATH = "./src/data/input/products.csv";
    public static final String SALES_CSV_PATH = "./src/data/input/sales.csv";

    // Rutas de salida para análisis de stock
    public static final String LOW_STOCK_CSV_PATH = "./src/data/output/low_stock_report.csv";
    public static final String HIGH_STOCK_CSV_PATH = "./src/data/output/high_stock_report.csv";

    // Rutas de salida para análisis de ventas
    public static final String PRODUCT_REPORT_PATH = "./src/data/output/product_sales_report.csv";
    public static final String DATE_REPORT_PATH = "./src/data/output/sales_by_date_report.csv";
}