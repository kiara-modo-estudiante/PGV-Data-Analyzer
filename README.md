# Parallel Sales and Inventory Analyzer

**PGV: UD1 – Práctica – Programación multiproceso**

En esta práctica utilizaré procesos independientes para resolver tareas en paralelo, concretamente resolveré una serie de necesidades empresariales clave:

1. **Análisis de Stock**  
   Este módulo analizará el inventario actual para identificar productos con bajo stock, exceso de inventario o rotación lenta. Se generarán informes detallados para facilitar la toma de decisiones.

2. **Análisis de Ventas**  
   Este módulo procesará los datos de ventas para identificar tendencias, productos más vendidos y períodos de alta o baja demanda. Los resultados ayudarán a optimizar las estrategias de ventas.

## Tecnologías utilizadas

- **Java**: Lenguaje principal para la implementación de la lógica de negocio.
- **Procesos independientes**: Uso de `ProcessBuilder` para ejecutar tareas en paralelo.
- **Archivos CSV**: Fuente de datos para el análisis de inventario y ventas.

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
   javac -d bin src/*.java
   ```
4. Ejecuta el programa:
   ```bash
   java -cp bin Main
   ```

## Estructura del proyecto

- `src/`: Contiene el código fuente.
- `files/`: Archivos de datos de ejemplo (CSV).
- `bin/`: Archivos compilados.

## Licencia

Fines educativos.
