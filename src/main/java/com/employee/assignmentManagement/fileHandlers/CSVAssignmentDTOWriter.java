package com.employee.assignmentManagement.fileHandlers;

import com.employee.assignmentManagement.exceptions.FileException;
import com.employee.assignmentManagement.models.AssignmentDTO;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Component
public class CSVAssignmentDTOWriter implements AssignmentDTOWriter {
    public void write(List<AssignmentDTO> items, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Serializable item : items) {
                writer.write(items.toString());
                writer.write(System.lineSeparator());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new FileException(ex.getMessage());
        }
    }
}
