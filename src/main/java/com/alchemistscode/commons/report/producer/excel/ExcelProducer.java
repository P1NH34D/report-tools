package com.alchemistscode.commons.report.producer.excel;

import com.alchemistscode.commons.tools.contract.Reportable;
import com.alchemistscode.commons.tools.label.Component;
import com.alchemistscode.commons.tools.util.ReportableTool;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ExcelProducer {
    private static final int HEADERS_HEIGHT = 35;
    private static final int COLUMN_WIDTH = 25;
    private static final int ROWS_HEIGHT = 15;
    private int currentRowIndex = 0;

    private ExcelObject excelObject;

    private Sheet sheet;

    public void initialize() {
        this.excelObject = new ExcelObject();
        currentRowIndex = 0;
    }

    public void createSheet(String name) {
        this.sheet = excelObject.getWorkbook().createSheet(name);
        sheet.setDefaultColumnWidth(COLUMN_WIDTH);
    }

    public void addHeaders(String[] headers){
        Row row = sheet.createRow(currentRowIndex);
        row.setHeightInPoints(HEADERS_HEIGHT);
        for (int column = 0 ; column < headers.length ; column++) {
            Cell cell = row.createCell(column);
            cell.setCellValue(headers[column]);
            cell.setCellStyle(excelObject.getExcelStyle().getHeaderStyle());
        }
        currentRowIndex ++;
    }

    public void createRow(Reportable data){
        Row row = this.sheet.createRow(currentRowIndex);
        row.setHeightInPoints(ROWS_HEIGHT);
        ReportableTool.getValuesRow(excelObject, row, data);
        currentRowIndex ++ ;
    }

    public void save(ByteArrayOutputStream outStream) throws IOException {
        try {
            excelObject.getWorkbook().write(outStream);
        } finally {
            excelObject.getWorkbook().close();
        }
    }
}
