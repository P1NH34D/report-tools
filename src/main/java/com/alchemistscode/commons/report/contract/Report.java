package com.alchemistscode.commons.report.contract;

import com.alchemistscode.commons.tools.contract.Reportable;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 *
 * @author arturo.tovar@alchemistscode.com
 * @version 1.0.0
 * @version 1.0 Ago. 02, 2019.
 */
public interface Report {
    ByteArrayOutputStream generateReport(List<? extends Reportable> reportables);
}
