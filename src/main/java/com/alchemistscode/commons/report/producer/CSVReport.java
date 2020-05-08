package com.alchemistscode.commons.report.producer;

import com.alchemistscode.commons.tools.contract.Reportable;
import com.alchemistscode.commons.report.contract.Report;
import com.alchemistscode.commons.tools.label.Component;
import com.alchemistscode.commons.tools.util.ReportableTool;
import com.opencsv.CSVWriter;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 *
 * @author  pinhead - arturo.tovar@kode.com.mx
 * @version 1.0.0
 * @since  Dic. 16, 2019.
 * @since  Ago. 02, 2019.
 */
@Component
public class CSVReport implements Report {
    private static final Logger logger = LoggerFactory.getLogger(CSVReport.class);

    @Override
    public ByteArrayOutputStream generateReport(List<? extends Reportable> reportables) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (OutputStreamWriter outWriter = new OutputStreamWriter(outputStream, Charsets.UTF_8)){
            CSVWriter csvWriter = new CSVWriter(outWriter, ',','"','"',"\n");
            csvWriter.writeNext(ReportableTool.getTitles(reportables.get(0)));
            reportables.forEach(element -> csvWriter.writeNext(ReportableTool.getValues(element)));
        } catch (IOException e) {
            logger.error("Error generating csv report: {}", e.getMessage());
        }
        return outputStream;
    }
}
