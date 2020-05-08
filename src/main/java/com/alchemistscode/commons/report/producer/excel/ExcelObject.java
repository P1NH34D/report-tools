package com.alchemistscode.commons.report.producer.excel;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelObject {
    @Getter
    private Workbook workbook;
    @Getter
    private ExcelStyle excelStyle;

    public ExcelObject() {
        this.workbook = new XSSFWorkbook();
        this.excelStyle = new ExcelStyle(workbook);
    }
}
