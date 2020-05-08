package com.alchemistscode.commons.tools.test;

import com.alchemistscode.commons.tools.model.ExampleAlterText;
import com.alchemistscode.commons.tools.model.ExampleFormat;
import com.alchemistscode.commons.tools.model.ExampleReportable;
import com.alchemistscode.commons.tools.util.ReportableTool;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;

public class TestValuesUtils {

    @Test
    public void testGetTitles(){
        String [] expect = {"Boolean 1","Boolean 2","Date","Double 1","Double 2","Integer 1","Integer 2","Long 1","Long 2","String"};
        String [] actual = ReportableTool.getTitles(ExampleReportable.builder().build());
        assertArrayEquals(expect, actual);
    }
    @Test
    public void testGetValues(){
        Date date = new Date(1588960366143L);
        String [] expect = {"Si","No", "08/05/2020","1.00", "2.00", "3", "4", "50000", "60000", "Example Texto"};
        String [] actual = ReportableTool.getValues(
                ExampleReportable.builder()
                        .varDate(date).varString("Example Texto")
                        .varBoolean(true).varBoolean2(false)
                        .varNumberSmall(3).varNumberSmall2(4)
                        .varNumberLarge(50000L).varNumberLarge2(60000L)
                        .varDouble(1.0d).varDouble2(2.0D)
                        .build());
        assertArrayEquals(expect, actual);
    }

    @Test
    public void testAlterText(){
        String [] expect = {"-","", "N/A","N/D"};
        String [] actual = ReportableTool.getValues(ExampleAlterText.builder().build());

        assertArrayEquals(expect, actual);
    }

    @Test
    public void testFormatNumber(){
        String [] expect = {"1,000", "1,000.00","95.00%" ,"$1,000.00","-","-"};
        String [] actual = ReportableTool.getValues(ExampleFormat.builder()
                .varNumber(1000)
                .varDecimal(1000d)
                .varCurrency(1000d)
                .varPercentage(95d)
                .build());

        assertArrayEquals(expect, actual);
    }

    @Test
    public void testFormatNumberDigits(){
        String [] expect = {"1,000", "1,000.10","95.10%" ,"$1,000.10","-","-"};
        String [] actual = ReportableTool.getValues(ExampleFormat.builder()
                .varNumber(1000.1)
                .varDecimal(1000.1d)
                .varCurrency(1000.1d)
                .varPercentage(95.1d)
                .build());

        assertArrayEquals(expect, actual);
    }

    @Test
    public void testFormatNumberDigits2(){
        String [] expect = {"1,000", "1,000.14","95.14%" ,"$1,000.14","-","-"};
        String [] actual = ReportableTool.getValues(ExampleFormat.builder()
                .varNumber(1000.14)
                .varDecimal(1000.14d)
                .varCurrency(1000.14d)
                .varPercentage(95.14d)
                .build());

        assertArrayEquals(expect, actual);
    }

    @Test
    public void testFormatNumberDigits3(){
        String [] expect = {"1,000", "1,000.14","95.14%" ,"$1,000.14","-","-"};
        String [] actual = ReportableTool.getValues(ExampleFormat.builder()
                .varNumber(1000.145)
                .varDecimal(1000.145d)
                .varCurrency(1000.145d)
                .varPercentage(95.145d)
                .build());

        assertArrayEquals(expect, actual);
    }

    @Test
    public void testFormatDate() {
        Date date= new Date(1588960366143L);
        String [] expect = {"-","-","-","-","08/05/2020", "08/05/2020 12:52:46"};
        String [] actual = ReportableTool.getValues(ExampleFormat.builder()
                .varDate(date)
                .varDateTime(date)
                .build());

        assertArrayEquals(expect, actual);
    }
}
