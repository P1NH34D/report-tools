package com.alchemistscode.commons.tools.model;

import com.alchemistscode.commons.tools.contract.Reportable;
import com.alchemistscode.commons.tools.label.ColumnTitle;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExampleReportable implements Reportable {
    @ColumnTitle(value = "Boolean 1")
    private boolean varBoolean;
    @ColumnTitle(value = "Boolean 2")
    private Boolean varBoolean2;
    @ColumnTitle(value = "Date")
    private Date varDate;
    @ColumnTitle(value = "Double 1")
    private double varDouble;
    @ColumnTitle(value = "Double 2")
    private Double varDouble2;
    @ColumnTitle(value = "Integer 1")
    private int varNumberSmall;
    @ColumnTitle(value = "Integer 2")
    private Integer varNumberSmall2;
    @ColumnTitle(value = "Long 1")
    private long varNumberLarge;
    @ColumnTitle(value = "Long 2")
    private Long varNumberLarge2;
    @ColumnTitle(value = "String")
    private String varString;

}
