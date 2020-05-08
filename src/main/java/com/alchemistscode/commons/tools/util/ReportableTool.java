package com.alchemistscode.commons.tools.util;

import com.alchemistscode.commons.tools.contract.Reportable;
import com.alchemistscode.commons.tools.enums.AlternateText;
import com.alchemistscode.commons.tools.enums.Formatter;
import com.alchemistscode.commons.tools.label.ColumnTitle;
import com.alchemistscode.commons.tools.label.SheetTitle;
import com.alchemistscode.commons.report.producer.excel.ExcelObject;
import com.alchemistscode.commons.report.producer.excel.ExcelStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportableTool {
    private static final Logger logger = LoggerFactory.getLogger(ReportableTool.class);

    private static Locale locale = new Locale("es","MX");
    private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    private static NumberFormat numberFormatter = NumberFormat.getIntegerInstance(locale);
    private static DecimalFormat decimalFormatter = new DecimalFormat("#,###.##");
    private static Reportable reportable;
    private static Field[] fields;
    private static int size;
    private static int column;

    private ReportableTool() { throw new IllegalStateException("Utility class");}

    public static String[] getTitles(Reportable data){
        initialize(data);
        List<String> response = new ArrayList<>();
        for (int position = 0; position < size; position++) {
            if(isAnnotated(position)) {
                response.add(getTitle(position));
            }
        }
        return response.toArray(new String[0]);
    }

    public static String[] getValues(Reportable data){
        initialize(data);
        List<String> values = new ArrayList<>();
        for (int position = 0; position < size; position++) {
            if (isAnnotated(position)) {
                values.add(getValue(position));
            }
        }
        return values.toArray(new String[0]);
    }

    public static String getSheetTitle(Reportable reportable){
        SheetTitle sheetTitle = reportable.getClass().getDeclaredAnnotation(SheetTitle.class);
        return sheetTitle != null ? sheetTitle.value() : reportable.getClass().getSimpleName();
    }

    public static Row getValuesRow(ExcelObject excelObject, Row row, Reportable data){
        initialize(data);
        for (int position = 0; position < size; position++) {
            if (isAnnotated(position)) {
                getCell(excelObject.getExcelStyle(), row, position);
            }
        }
        return row;
    }

    private static void initialize(Reportable reportable) {
        ReportableTool.reportable = reportable;
        fields = ReportableTool.reportable.getClass().getDeclaredFields();
        size = ReportableTool.fields.length ;
        column = 0;
    }

    private static boolean isAnnotated(int position){
        Optional<Annotation> annota = Arrays.stream(fields[position].getAnnotations())
                .filter(annotation -> annotation instanceof ColumnTitle).findAny();
        return  annota.isPresent();
    }

    private static String getAlter(int position){
        for (Annotation annotation : fields[position].getAnnotations()) {
            if (annotation instanceof ColumnTitle) {
                return ((ColumnTitle) annotation).alternativeText().getText();
            }
        }
        return AlternateText.DEFAULT.getText();
    }

    private static Integer getFormat(int position){
        for (Annotation annotation : fields[position].getAnnotations()) {
            if (annotation instanceof ColumnTitle) {
                return ((ColumnTitle) annotation).format().ordinal();
            }
        }
        return Formatter.DEFAULT.ordinal();
    }

    private static String getTitle(int position) {
        for (Annotation annotation : fields[position].getAnnotations()) {
            if (annotation instanceof ColumnTitle) {
                String value = ((ColumnTitle) annotation).value();
                return value.isEmpty() ? fields[position].getName() : value;
            }
        }
        return "";
    }

    private static String getValue(int position) {
        if (isBoolean(position))
            return boolean2String(position);
        else if (isDate(position))
            return date2String(position);
        else if(isDouble(position)){
            return formatter(position);
        }
        else if (isInteger(position))
            return integer2String(position);
        else if(isLong(position))
            return long2String(position);

        return field2String(position);
    }

    private static String formatter(int position) {
        String value;
        switch (getFormat(position)){
            case 1:
                value = number2String(position);
                break;
            case 2:
                value = decimal2String(position);
                break;
            case 3:
                value = percentage2String(position);
                break;
            case 4:
                value = currency2String(position);
                break;
            default:
                value = field2String(position);
        }
        return value;


    }

    private static boolean isBoolean(int position){
        return fields[position].getType().isAssignableFrom(java.lang.Boolean.class)
                || fields[position].getType().getName().equals("boolean");
    }

    private static boolean isDate(int position) {
        return fields[position].getType().isAssignableFrom(java.util.Date.class);
    }

    private static boolean isDouble(int position){
        return fields[position].getType().isAssignableFrom(java.lang.Double.class)
                || fields[position].getType().getName().equals("double");
    }

    private static boolean isInteger(int position){
        return fields[position].getType().isAssignableFrom(java.lang.Integer.class)
                || fields[position].getType().getName().equals("int");
    }

    private static boolean isLong(int position){
        return fields[position].getType().isAssignableFrom(java.lang.Long.class)
                || fields[position].getType().getName().equals("long");
    }

    private static Object getFieldValue(int position){
        Object response = null;
        try {
            String fieldName = fields[position].getName();
            PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, reportable.getClass());
            response =  descriptor.getReadMethod().invoke(reportable);
        } catch (IllegalArgumentException | IllegalAccessException | IntrospectionException | InvocationTargetException e) {
            logger.error("Error get field value of position {}: {}", position, e.getMessage());
        }
        return response;

    }

    private static String field2String(int position) {
        Object value = getFieldValue(position);
        return value == null || String.valueOf(value).isEmpty()
                ? getAlter(position) : String.valueOf(value);
    }

    private static String date2String(int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Object value = getFieldValue(position);
        Date newData = (Date) value;

        return newData != null
                ? getFormat(position).equals(5) ? dateTimeFormat.format(newData) : dateFormat.format(newData)
                : getAlter(position);
    }

    private static String currency2String(int position){
        Object value = getFieldValue(position);
        Double newData = (Double) value;
        return newData != null  && newData > 0 ? currencyFormatter.format(newData) : getAlter(position);
    }

    private static String decimal2String(int position){
        Object value = getFieldValue(position);
        Double newData = (Double) value;



        if(newData != null  && newData > 0){
            String format = decimalFormatter.format(newData);
            Integer idx = format.lastIndexOf(".") + 1;
            String decimals = idx > 0 ? format.substring(idx) : "";

            if(decimals.length() == 0)
                return format.concat(".00");
            if(decimals.length() == 1)
                return format.concat("0");

            return format;
        }
        else {
            return getAlter(position);
        }

    }

    private static String number2String(int position){
        Object value = getFieldValue(position);
        Double newData = (Double) value;
        return newData != null  && newData > 0 ? numberFormatter.format(newData) : getAlter(position);
    }

    private static String percentage2String(int position){
        Object value = getFieldValue(position);
        Double newData = (Double) value;

        if(newData != null  && newData > 0){
            String format = decimalFormatter.format(newData);
            Integer idx = format.lastIndexOf(".") + 1;
            String decimals = idx > 0 ? format.substring(idx) : "";

            if(decimals.length() == 0)
                return format.concat(".00%");
            if(decimals.length() == 1)
                return format.concat("0%");

            return format.concat("%");
        }
        else {
            return getAlter(position);
        }
    }

    private static String integer2String(int position){
        Object value = getFieldValue(position);
        Integer newData = (Integer) value;
        return newData != null  && newData > 0 ? String.valueOf(newData) : getAlter(position);
    }

    private static String long2String(int position){
        Object value = getFieldValue(position);
        Long newData = (Long) value;

        return newData != null  && newData > 0 ? String.valueOf(newData) : getAlter(position);
    }

    private static String boolean2String(int position){
        Object value = getFieldValue(position);
        Boolean newData = (Boolean) value;
        return newData != null && newData ? "Si" : "No";
    }

    private static HorizontalAlignment getAlignment(int position){
        for (Annotation annotation : fields[position].getAnnotations()) {
            if (annotation instanceof ColumnTitle) {
                String aux = getValue(position);
                return AlternateText.valuesText().contains(aux)
                        ? HorizontalAlignment.CENTER : ((ColumnTitle) annotation).alignment() ;
            }
        }
        return HorizontalAlignment.LEFT;
    }

    private static void getCell(ExcelStyle excelStyle, Row row, int position){
        Cell cell = row.createCell(column);
        XSSFCellStyle cellStyle;

        if (isBoolean(position)){
            cellStyle = excelStyle.getBooleanStyle();
        }
        else if (isDate(position)) {
            cellStyle = excelStyle.getDateStyle();
        }
        else if(isDouble(position)){
            cellStyle = excelStyle.getCurrencyStyle();
        }
        else if(isInteger(position) || isLong(position)){
            cellStyle = excelStyle.getNumberStyle();
        }
        else{
            cellStyle = excelStyle.getDefaultStyle(getAlignment(position));
        }
        cell.setCellValue(getValue(position));
        cell.setCellStyle(cellStyle);
        column++;
    }
}
