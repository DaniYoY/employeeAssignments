package com.employee.assignmentManagement.fileHandlers;

import com.employee.assignmentManagement.models.AssignmentDTO;

import java.util.List;

public interface AssignmentDTOWriter {
    void write(List<AssignmentDTO> items, String filename);
}
