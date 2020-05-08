package com.alchemistscode.commons.tools.model;

import com.alchemistscode.commons.tools.contract.Reportable;
import com.alchemistscode.commons.tools.label.ColumnTitle;
import com.alchemistscode.commons.tools.label.SheetTitle;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@SheetTitle("Example")
public class ExampleAnnotations implements Reportable {
    @ColumnTitle(value = "")
    private String name;
    @ColumnTitle
    private String lastName;
    @ColumnTitle
    private Integer age;
    @ColumnTitle
    private Date birthday;
}
