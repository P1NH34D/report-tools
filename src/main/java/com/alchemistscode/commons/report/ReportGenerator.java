package com.alchemistscode.commons.report;

import com.alchemistscode.commons.report.enums.ReportType;
import com.alchemistscode.commons.report.producer.CSVReport;
import com.alchemistscode.commons.report.producer.XLSReport;
import com.alchemistscode.commons.report.wrapper.ReportParameter;
import com.alchemistscode.commons.report.wrapper.ReportResponse;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author pinhead arturo.tovar@alchemistscode.com
 * @version 1.0 Ago. 02, 2019.
 */
@SuppressWarnings("unused")
public class ReportGenerator {

    private final CSVReport csvReport;
    private final XLSReport xlsReport;

    @SuppressWarnings("unused")
    public ReportGenerator() {
        this.csvReport = new CSVReport();
        this.xlsReport = new XLSReport();
    }

    @SuppressWarnings("unused")
    public ReportResponse generateReport(ReportParameter parameter){
        String extension = parameter.getExtension();
        ReportType reportType = ReportType.getReportType(extension);

        if(reportType == null) {
            return ReportResponse.builder().errorCode("The report cannot be generated. Undefined report type").build();
        }
        else if(parameter.getData() == null || parameter.getData().isEmpty()){
            return ReportResponse.builder().errorCode("The report cannot be generated. There is no data").build();
        }
        else {
            ByteArrayOutputStream buffer = null;
            String content   = "";
            String fileName  = "";
            String errorCode;

            switch (extension){
                case "xls":
                case "xlsx":
                    buffer = xlsReport.generateReport(parameter.getData());
                    break;
                case "csv":
                    buffer = csvReport.generateReport(parameter.getData());
                    break;
                default:
            }

            if(buffer != null){
                content   = reportType.getContent();
                fileName  = generateFileName(parameter);
                errorCode = "Report generated successfully";
            } else {
                errorCode = "empty: It is not possible to generate the report";
            }

            return ReportResponse.builder().fileName(fileName).errorCode(errorCode)
                    .content(content).buffer(buffer)
                    .build();
        }
    }

    private String generateFileName(ReportParameter parameters){
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
        return parameters.getReportName() + "_" + dataFormat.format(new Date()) +
                "." + parameters.getExtension();
    }

}
