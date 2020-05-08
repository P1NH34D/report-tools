package com.alchemistscode.commons.report.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author  pinhead - arturo.tovar@kode.com.mx
 * @version 1.0.0
 * @since  Ago. 02, 2019.
 */
public enum ReportType {
    /**
     * Constante para el MIMETYPE de XLS
     */
    XLS("xls", "application/vnd.ms-excel"),

    /**
     * Constante para el MIMETYPE de XLSX
     */
    XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),

    /**
     * Constante para el MIMETYPE de CSV
     */
    CSV("csv", "application/csv; charset=UTF-8");


    /**
     * Devuelve el valor contenido en {@link #extension} de la constante que
     * esta siendo usada
     */
    private @Getter
    String extension;
    /**
     * Devuelve el valor contenido en {@link #content} de la constante que esta
     * siendo usada
     */
    private @Getter
    String content;

    ReportType(String extension, String content) {
        this.extension = extension;
        this.content = content;
    }

    public static List<String> getExtensions(){
        List<String> extensions = new ArrayList<>();
        Arrays.stream(ReportType.values()).forEach(ft -> extensions.add(ft.getExtension() ));
        return extensions;
    }

    public static ReportType getReportType(String extension){
        for (ReportType reportType : ReportType.values()) {
            if(reportType.getExtension().equals(extension)){
                return reportType;
            }
        }
        return null;
    }
}
