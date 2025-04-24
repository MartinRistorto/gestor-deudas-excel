package gestor;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

    public static void main(String[] args) {
        String[] columns = {"Nombre", "Monto", "Año", "Tipo de Deuda"};
        Object[][] data = {
            {"Deuda1", 10090.0, 2022, "Administrativa"},
            {"Deuda2", 50096.0, 2023, "Judicial"},
            {"Deuda3", 75670.0, 2022, "Prejudicial"},
            {"Deuda4", 100960.0, 2022, "Administrativa"},
            {"Deuda5", 50096.0, 2023, "Judicial"},
            {"Deuda6", 75670.0, 2022, "Prejudicial"},
            {"Deuda7", 10900.0, 2022, "Administrativa"},
            {"Deuda8", 50670.0, 2023, "Judicial"},
            {"Deuda9", 7500.0, 2022, "Prejudicial"},
            {"Deuda10", 10700.0, 2022, "Administrativa"},
            {"Deuda11", 5000.0, 2023, "Judicial"},
            {"Deuda12", 7580.0, 2022, "Prejudicial"},
        };

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Deudas");

        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Fill data rows
        int rowNum = 1;
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowData.length; i++) {
                Cell cell = row.createCell(i);
                if (rowData[i] instanceof String) {
                    cell.setCellValue((String) rowData[i]);
                } else if (rowData[i] instanceof Double) {
                    cell.setCellValue((Double) rowData[i]);
                } else if (rowData[i] instanceof Integer) {
                    cell.setCellValue((Integer) rowData[i]);
                }
            }
        }

        // Autosize columns
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream("deudas.xlsx")) {
            workbook.write(fileOut);
            System.out.println("Archivo deudas.xlsx generado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al generar el archivo: " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }
}
