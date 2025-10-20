# Data (Sales and Stock) Analyzer

**PGV: UD1 – Práctica – Programación multiproceso**

## Objetivo

En esta práctica utilizaré procesos independientes para resolver tareas en paralelo concurrentemente, concretamente resolveré una serie de necesidades empresariales clave:

1. **Análisis de Stock** [ [StockAnalyzer](src/net/salesianos/modules/StockAnalyzer.java) ]:

   Este módulo analizará el inventario actual para identificar productos con bajo stock o stock muy elevado. La cantidad de unidades en stock para que dicho producto cumpla estas condiciones serán elegidas por el usuario. Se generarán informes en formato .csv mostrando los productos que cumplan estas condiciones.

2. **Análisis de Ventas** [ [SalesAnalyzer](src/net/salesianos/modules/SalesAnalyzer.java) ]:

   Este módulo procesará los datos de ventas para identificar el número de ventas de cada producto y el número de ventas que se han efectuado por día. Los resultados también serán mostrados en formato .csv.

De esta manera, facilitaremos la toma de decisiones respecto a la reposición del stock de productos y su posterior venta, conociendo mejor las tendencias de compra en nuestra tienda que, en este caso, será una tienda de informática.

Todos los fichero .csv generados serán guardados en un directorio output, que se creará con la primera consulta o ejecución del programa. Estos podrán ser visualizados comodamente en la consola una vez creados.

## Cómo ejecutar el proyecto

1. Clona el repositorio:
   ```bash
   git clone https://github.com/kiara-modo-estudiante/PGV-Data-Analyzer.git
   ```
2. Navega al directorio del proyecto:
   ```bash
   cd PGV-Data-Analyzer
   ```
3. Compila el proyecto:
   ```bash
   javac -d bin src/**/*.java
   ```
4. Ejecuta el programa:

   ```bash
   java -cp bin App
   ```

**⚠️ IMPORTANTE: La no compilación previa del proyecto con el comando ofrecido en el paso 3 puede impedir el correcto funcionamiento del mismo.**

## Estructura del proyecto

```bash
src
├── App.java # Clase principal que inicia la aplicación
├── data
│     ├── input
│     │      ├── products.csv # Archivo de entrada con datos de productos
│     │      └── sales.csv # Archivo de entrada con datos de ventas
│     └── output
│            └── (generados por usuario) # Archivos de salida generados por el programa
└── net
     └── salesianos
         ├── config
         │      └── PathsConfig.java # Configuración de rutas para archivos y directorios
         ├── modules
         │      ├── SalesAnalyzer.java # Módulo para analizar datos de ventas
         │      └── StockAnalyzer.java # Módulo para analizar datos de inventario
         └── utils
                ├── ConsoleColors.java # Utilidad para colorear texto en consola
                ├── FileHelper.java # Utilidad para operaciones con archivos
                └── ProcessLauncher.java # Utilidad para lanzar procesos concurrentes
```

## Manual de Usuario

En el manual de usuario encontrarás una guía sobre cómo utilizar el programa, desde la configuración inicial hasta la interpretación de los resultados generados.

Puedes acceder al manual completo en el siguiente enlace: [Manual de Usuario](docs/manual_usuario.md)

## Licencia

Fines educativos.
