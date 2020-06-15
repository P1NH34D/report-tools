package com.alchemistscode.commons.report.producer;

import com.alchemistscode.commons.tools.contract.Reportable;
import com.alchemistscode.commons.report.producer.excel.ExcelProducer;
import com.alchemistscode.commons.report.contract.Report;
import com.alchemistscode.commons.tools.label.Component;
import com.alchemistscode.commons.tools.util.ReportableTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author  pinhead - arturo.tovar@alchemistscode.com
 * @version 1.0.0
 * @since  Ago. 02, 2019.
 */
@Component
public class XLSReport implements Report {
    private static final Logger logger = LoggerFactory.getLogger(XLSReport.class);
    private final ExcelProducer excelProducer;

    public XLSReport() {
        excelProducer = new ExcelProducer();
    }

    @Override
    public ByteArrayOutputStream generateReport(List<? extends Reportable> reportables) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try{
            excelProducer.initialize();
            excelProducer.createSheet(ReportableTool.getSheetTitle(reportables.get(0)) );
            excelProducer.addHeaders(ReportableTool.getTitles(reportables.get(0)));
            reportables.forEach(data -> excelProducer.createRow(data));
            excelProducer.save(outputStream);
        } catch (IOException e) {
            logger.error("Error generating xls|xlsx report: {}", e.getMessage());
        }

        return outputStream;
    }
}
