package com.alchemistscode.commons.tools.model;

import com.alchemistscode.commons.tools.contract.Reportable;
import com.alchemistscode.commons.tools.enums.AlternateText;
import com.alchemistscode.commons.tools.label.ColumnTitle;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExampleAlterText implements Reportable {
    @ColumnTitle(alternativeText = AlternateText.DEFAULT)
    private String alterDefault;
    @ColumnTitle(alternativeText = AlternateText.EMPTY)
    private String alterEmpty;
    @ColumnTitle(alternativeText = AlternateText.NOT_APPLY)
    private String alterNotApply;
    @ColumnTitle(alternativeText = AlternateText.NOT_AVAILABLE)
    private String alterNotAvailable;
}
