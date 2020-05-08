package com.alchemistscode.commons.tools.test;

import com.alchemistscode.commons.report.ReportGenerator;
import com.alchemistscode.commons.report.wrapper.ReportParameter;
import com.alchemistscode.commons.report.wrapper.ReportResponse;
import com.alchemistscode.commons.tools.model.ExampleAnnotations;
import com.alchemistscode.commons.tools.util.ReportableTool;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestReport {
    @Test
    public void testSheetTitle() {
        String expect = "Example";
        String actual = ReportableTool.getSheetTitle(ExampleAnnotations.builder().build());
        assertEquals(expect, actual);
    }

    @Test
    public void testFailReportTypeCSV() {
        ReportGenerator generator = new ReportGenerator();

        ReportResponse expect = ReportResponse.builder()
                .errorCode("The report cannot be generated. Undefined report type")
                .build();

        ReportResponse actual = generator.generateReport(ReportParameter.builder()
                .reportName("Test.csv")
                .build());

        assertEquals(expect, actual);
    }

    @Test
    public void testFailDataCSV() {
        ReportGenerator generator = new ReportGenerator();

        ReportResponse expect = ReportResponse.builder()
                .errorCode("The report cannot be generated. There is no data")
                .build();

        ReportResponse actual = generator.generateReport(ReportParameter.builder()
                .reportName("Test")
                .extension("csv")
                .build());

        assertEquals(expect, actual);
    }

    @Test
    public void testCSV() throws IOException {
        ReportGenerator generator = new ReportGenerator();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        List<ExampleAnnotations> data = new ArrayList<>();

        try {
            data = Arrays.asList(
                    ExampleAnnotations.builder().name("Arturo").lastName("Tovar").age(35).birthday(dateFormat.parse("13-03-1985")).build()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String expect = "Report generated successfully";
        ReportResponse actual = generator.generateReport(ReportParameter.builder()
                .reportName("Test")
                .extension("csv")
                .data(data)
                .build());

        assertEquals(expect, actual.getErrorCode());
    }

    @Test
    public void testXLS() throws IOException {
        ReportGenerator generator = new ReportGenerator();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        List<ExampleAnnotations> data = new ArrayList<>();

        try {
            data = Arrays.asList(
                    ExampleAnnotations.builder().name("Arturo").lastName("Tovar").age(35).birthday(dateFormat.parse("13-03-1985")).build()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String expect = "Report generated successfully";
        ReportResponse actual = generator.generateReport(ReportParameter.builder()
                .reportName("Test")
                .extension("xls")
                .data(data)
                .build());

        assertEquals(expect, actual.getErrorCode());
    }


}
