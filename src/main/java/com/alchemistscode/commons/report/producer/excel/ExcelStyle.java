package com.alchemistscode.commons.report.producer.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.awt.Color;

public class ExcelStyle {
    private static final Color AZUL = new Color(79, 129, 189);
    private final XSSFCellStyle headerStyle;
    private final XSSFCellStyle booleanStyle;
    private final XSSFCellStyle defaultStyle;
    private final XSSFCellStyle rightStyle;
    private final XSSFCellStyle currencyStyle;
    private final XSSFCellStyle numberStyle;
    private final XSSFCellStyle dateStyle;
    private final XSSFCellStyle titleTableStyle;
    private final XSSFCellStyle valueTableStyle;

    public ExcelStyle(Workbook wb) {
        headerStyle = headerStyle(wb);
        booleanStyle = generalStyle(wb, HorizontalAlignment.CENTER);
        defaultStyle = generalStyle(wb, HorizontalAlignment.LEFT);
        rightStyle   = generalStyle(wb, HorizontalAlignment.RIGHT);
        currencyStyle = currencyStyle(wb);
        numberStyle = numberStyle(wb);
        dateStyle = dateStyle(wb);
        titleTableStyle = titleTableStyle(wb);
        valueTableStyle = valueTableStyle(wb);
    }

    public XSSFCellStyle getHeaderStyle() {
        return headerStyle;
    }

    public XSSFCellStyle getBooleanStyle() {
        return booleanStyle;
    }


    public XSSFCellStyle getDefaultStyle(HorizontalAlignment alignment) {
        XSSFCellStyle stringStyle;

        switch (alignment){
            case CENTER:
                stringStyle = booleanStyle;
                break;
            case RIGHT:
                stringStyle = rightStyle;
                break;
            default:
                stringStyle = defaultStyle;
        }
        return stringStyle;
    }

    public XSSFCellStyle getCurrencyStyle() {
        return currencyStyle;
    }

    public XSSFCellStyle getNumberStyle() {
        return numberStyle;
    }

    public XSSFCellStyle getDateStyle() {
        return dateStyle;
    }

    public XSSFCellStyle getTitleTableStyle() {
        return titleTableStyle;
    }

    public XSSFCellStyle getValueTableStyle() {
        return valueTableStyle;
    }

    private XSSFCellStyle headerStyle (Workbook wb) {
        XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
        cellAlignment(cellStyle, HorizontalAlignment.CENTER);
        backgroundColor(cellStyle, AZUL);
        borderColor(cellStyle, Color.BLACK);
        fontColor(wb, cellStyle, IndexedColors.WHITE.index, true);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private XSSFCellStyle generalStyle(Workbook workbook, HorizontalAlignment alignment){
        XSSFCellStyle cellStyle = genericStyle(workbook);
        cellAlignment(cellStyle, alignment, VerticalAlignment.CENTER);
        return cellStyle;
    }

    private XSSFCellStyle currencyStyle(Workbook workbook){
        XSSFCellStyle cellStyle = genericStyle(workbook);
        cellAlignment(cellStyle, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);
        cellStyle.setDataFormat(8);
        return cellStyle;
    }

    private XSSFCellStyle numberStyle(Workbook workbook){
        DataFormat format = workbook.createDataFormat();
        XSSFCellStyle cellStyle = genericStyle(workbook);
        cellAlignment(cellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        cellStyle.setDataFormat(format.getFormat("#.###############"));
        return cellStyle;
    }

    private XSSFCellStyle dateStyle (Workbook workbook) {
        XSSFCellStyle cellStyle = genericStyle(workbook);
        cellStyle.setDataFormat(
                workbook.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy"));
        return cellStyle;
    }

    private XSSFCellStyle titleTableStyle(Workbook workbook){
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        fontColor(workbook, cellStyle, IndexedColors.BLACK.index,true);
        cellAlignment(cellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        borderColor(cellStyle, Color.BLACK);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private XSSFCellStyle valueTableStyle(Workbook workbook){
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        borderColor(cellStyle, Color.BLACK);
        cellAlignment(cellStyle,HorizontalAlignment.LEFT);
        fontColor(workbook, cellStyle, IndexedColors.BLACK.index, false);
        return cellStyle;
    }

    private XSSFCellStyle genericStyle(Workbook workbook){
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        cellAlignment(cellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        borderColor(cellStyle, Color.BLACK);
        return cellStyle;
    }

    private void cellAlignment (CellStyle cellStyle, HorizontalAlignment horizontalAlignment) {
        cellStyle.setAlignment(horizontalAlignment);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    }

    private void cellAlignment (CellStyle cellStyle, HorizontalAlignment horizontalAlignment,
                                VerticalAlignment verticalAlignment) {
        cellStyle.setAlignment(horizontalAlignment);
        cellStyle.setVerticalAlignment(verticalAlignment);
    }

    private void backgroundColor (XSSFCellStyle cellStyle, Color color) {
        cellStyle.setFillForegroundColor(new XSSFColor(color, new DefaultIndexedColorMap()));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    private void borderColor (XSSFCellStyle cellStyle, Color color) {
        XSSFColor xColor = new XSSFColor(color, new DefaultIndexedColorMap());
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(xColor);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(xColor);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(xColor);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(xColor);
    }

    private void fontColor (Workbook wb, CellStyle cellStyle, short color, boolean bold) {
        Font font = wb.createFont();
        font.setColor(color);
        font.setBold(bold);
        cellStyle.setFont(font);
    }
}
