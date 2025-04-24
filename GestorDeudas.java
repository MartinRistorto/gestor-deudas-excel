package gestor;

import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GestorDeudas {
    private List<Deuda> deudas;
    private Set<String> tiposDeuda;

    public GestorDeudas() {
        deudas = new ArrayList<>();
        tiposDeuda = new HashSet<>(Arrays.asList("Administrativa", "Judicial", "Prejudicial"));
    }

    public void agregarTipoDeuda(String tipo) {
        tiposDeuda.add(tipo);
    }

    public Set<String> getTiposDeuda() {
        return tiposDeuda;
    }

    public void agregarDeuda(String nombre, double monto, int año, String tipoDeuda) {
        deudas.add(new Deuda(nombre, monto, año, tipoDeuda));
    }

    public void mostrarDeudasPorAño(int año) {
        System.out.println("\nDeudas del año " + año + ":");
        deudas.stream().filter(d -> d.getAño() == año).forEach(System.out::println);
    }

    public void mostrarDeudasPorTipo(String tipo) {
        System.out.println("\nDeudas de tipo " + tipo + ":");
        deudas.stream().filter(d -> d.getTipoDeuda().equalsIgnoreCase(tipo)).forEach(System.out::println);
    }

    public void mostrarTodasLasDeudas() {
        System.out.println("\nTodas las deudas:");
        deudas.forEach(System.out::println);
    }

    public List<Deuda> getDeudas() {
        return deudas;
    }

    public void marcarDeudaComoPagada(int indice) {
        if (indice >= 0 && indice < deudas.size()) {
            deudas.get(indice).marcarComoPagada();
        }
    }

    public void importarDeudasDesdeExcel(String rutaArchivo) {
        try (FileInputStream file = new FileInputStream(new File(rutaArchivo));
             Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                String nombre = row.getCell(0).getStringCellValue();
                double monto = row.getCell(1).getNumericCellValue();
                int año = (int) row.getCell(2).getNumericCellValue();
                String tipoDeuda = row.getCell(3).getStringCellValue();
                agregarDeuda(nombre, monto, año, tipoDeuda);
            }
            System.out.println("Deudas importadas exitosamente!");
        } catch (IOException e) {
            System.out.println("Error al importar el archivo: " + e.getMessage());
        }
    }

    public void guardarDeudasEnExcel(String rutaArchivo) {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(new File(rutaArchivo))) {

            Sheet sheet = workbook.createSheet("Deudas");

            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nombre");
            headerRow.createCell(1).setCellValue("Monto");
            headerRow.createCell(2).setCellValue("Año");
            headerRow.createCell(3).setCellValue("Tipo de Deuda");

            // Llenar las filas con las deudas
            int rowNum = 1;
            for (Deuda deuda : deudas) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(deuda.getNombre());
                row.createCell(1).setCellValue(deuda.getMonto());
                row.createCell(2).setCellValue(deuda.getAño());
                row.createCell(3).setCellValue(deuda.getTipoDeuda());
            }

            workbook.write(fileOut);
            System.out.println("✅ Deudas guardadas en Excel exitosamente!");

        } catch (IOException e) {
            System.out.println("❌ Error al guardar en el archivo: " + e.getMessage());
        }
    }
}
