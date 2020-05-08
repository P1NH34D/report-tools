package com.alchemistscode.commons.report.wrapper;

import lombok.Builder;
import lombok.Data;

import java.io.ByteArrayOutputStream;

@Data
@Builder
public class ReportResponse {
    private String errorCode;
    private String content;
    private String fileName;
    private ByteArrayOutputStream buffer;
}
