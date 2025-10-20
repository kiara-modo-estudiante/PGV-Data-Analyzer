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

## Manual de Usuario

En el manual de usuario encontrarás una guía más extendida sobre cómo utilizar el programa, desde la configuración inicial hasta la interpretación de los resultados generados.

Puedes acceder al manual completo en el siguiente enlace: [Manual de Usuario](docs/manual_usuario.md)

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

## Requisitos para Cumplimentar la Práctica

Aquí añado los puntos indicados a conseguir y la manera en la que considero que los he conseguido.

| **Punto**                                                                    | **Descripción**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| ---------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Proceso principal que recoge información de uno o varios ficheros**        | El proceso principal está implementado en la clase `App`. Este recoge información de los ficheros CSV (`products.csv` y `sales.csv`) a través de los subprocesos `StockAnalyzer` y `SalesAnalyzer`. Los ficheros se leen utilizando la clase `FileHelper`, que encapsula la lógica de lectura y escritura de archivos.                                                                                                                                                                                                     |
| **Subprocesos que procesan datos con argumentos**                            | Los subprocesos están implementados en las clases `StockAnalyzer` y `SalesAnalyzer`. El subproceso `StockAnalyzer` recibe dos argumentos (`maxLowStock` y `minHighStock`) que determinan los límites para clasificar productos con stock bajo y alto. Ambos subprocesos procesan los datos de los ficheros CSV y generan resultados específicos.                                                                                                                                                                           |
| **Proceso principal ejecuta subprocesos con salidas redirigidas a ficheros** | El proceso principal (`App`) utiliza la clase `ProcessLauncher` para ejecutar los subprocesos de forma concurrente. Las salidas de los subprocesos (`StockAnalyzer` y `SalesAnalyzer`) se redirigen a ficheros CSV generados automáticamente en la carpeta de salida (`/src/data/output`).                                                                                                                                                                                                                                 |
| **Proceso principal muestra resoluciones tras finalizar los subprocesos**    | Una vez que los subprocesos han terminado, el proceso principal utiliza el método `FileHelper.printGeneratedCSVOutputs` para mostrar los resultados en consola si el usuario lo solicita. Si el usuario no desea ver los resultados en consola, se informa que los resultados están disponibles en los archivos CSV generados.                                                                                                                                                                                             |
| **Métodos adicionales para encapsular código repetitivo**                    | La clase `FileHelper` encapsula la lógica de lectura, escritura y manejo de archivos CSV, evitando la repetición de código. La clase `ProcessLauncher` encapsula la lógica de ejecución y manejo de subprocesos. Métodos como `filterValidProducts` y `filterProductsByStock` en `StockAnalyzer` encapsulan la lógica de filtrado de productos. También observamos esta separación de responsabilidades con las clases `PathsConfig` para el manejo de rutas y `ConsoleColors` para la impresión de colores en la consola. |

## Licencia

Fines educativos.
