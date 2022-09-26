package com.example.ecommerce.generator;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.example.ecommerce.homepage.HomeService;
import com.example.ecommerce.products.ProductService;
import com.example.ecommerce.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@AllArgsConstructor
public class ExcelGenerator {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }


    private void writeHeader() {
        sheet = workbook.createSheet("Info");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Brought products count", style);
        createCell(row, 1, "Brought products sum", style);
        createCell(row, 2, "Income from tax", style);
        createCell(row, 3, "Added products for today", style);
        createCell(row, 4, "Authorized users for today", style);
        createCell(row, 5, "Visits for today", style);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        Row row = sheet.createRow(rowCount++);
        int columnCount = 0;
        createCell(row, columnCount++, ProductService.broughtProductsCount, style);
        createCell(row, columnCount++, ProductService.broughtProductsSum, style);
        createCell(row, columnCount++, ProductService.incomeFromTax, style);
        createCell(row, columnCount++, ProductService.addedProductsCount, style);
        createCell(row, columnCount++, RegistrationService.authorizedUsersCount, style);
        createCell(row, columnCount++, HomeService.visitsForToday, style);

    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
