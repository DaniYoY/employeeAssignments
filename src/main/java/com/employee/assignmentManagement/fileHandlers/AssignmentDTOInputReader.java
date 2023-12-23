package com.employee.assignmentManagement.fileHandlers;

import com.employee.assignmentManagement.models.AssignmentDTO;

import java.io.InputStream;
import java.util.List;

public interface AssignmentDTOInputReader {
    List<AssignmentDTO> read(InputStream inputStream, boolean areHeadersPresent, String dateFormat);
}
