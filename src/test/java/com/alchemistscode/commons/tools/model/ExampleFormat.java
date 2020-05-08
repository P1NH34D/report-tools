package com.alchemistscode.commons.tools.model;

import com.alchemistscode.commons.tools.contract.Reportable;
import com.alchemistscode.commons.tools.enums.Formatter;
import com.alchemistscode.commons.tools.label.ColumnTitle;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExampleFormat implements Reportable {
    @ColumnTitle(format = Formatter.NUMBER)
    private double varNumber;
    @ColumnTitle(format = Formatter.DECIMAL)
    private double varDecimal;
    @ColumnTitle(format = Formatter.PERCENTAGE)
    private double varPercentage;
    @ColumnTitle(format = Formatter.CURRENCY)
    private double varCurrency;
    @ColumnTitle
    private Date varDate;
    @ColumnTitle(format = Formatter.DATETIME)
    private Date varDateTime;
}
