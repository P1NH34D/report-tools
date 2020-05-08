package com.alchemistscode.commons.report.wrapper;

import com.alchemistscode.commons.tools.contract.Reportable;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 *
 * @author  pinhead - arturo.tovar@kode.com.mx
 * @version 1.0.0
 * @since  Ago. 02, 2019.
 */
@Data
@Builder
public class ReportParameter {
    private String reportName;
    private String extension;
    private List<? extends Reportable> data;
}
