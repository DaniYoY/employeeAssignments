package com.employee.assignmentManagement.models;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

public class AssignmentFileUploadDTO {
    private boolean areHeaders;
    private String dateFormat;

    public AssignmentFileUploadDTO(boolean areHeaders, String dateFormat) {
        this.areHeaders = areHeaders;
        this.dateFormat = dateFormat;
    }

    @Value("${some.key:false}")
    public boolean areHeaders() {
        return areHeaders;
    }

    @Value("${some.key:YYYY-MM-DD}")
    public String getDateFormat() {
        return dateFormat;
    }

    public void setAreHeaders(boolean areHeaders) {
        this.areHeaders = areHeaders;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
