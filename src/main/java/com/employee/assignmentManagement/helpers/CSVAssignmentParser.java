package com.employee.assignmentManagement.helpers;

import com.employee.assignmentManagement.models.Assignment;
import com.employee.assignmentManagement.models.AssignmentDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CSVAssignmentParser {
    public static final int numberOfAssignmentFields = 4;

    public static final String INVALID_NUMBER_OF_ASSIGNMENT_FIELDS = "You have provided %d arguments to create an Assignment, but only %d are required";
    public static final String COMMA = ",";
    public static final int FIRST_ELEMENT = 0;
    public static final int SECOND_ELEMENT = 1;
    public static final int THIRD_ELEMENT = 2;
    public static final int FORTH_ELEMENT = 3;
    public static final String NULL = "null";

    public static AssignmentDTO parse(String line, String dateFormat) {
        String[] values = line.split(COMMA);
        if (values.length != numberOfAssignmentFields) {
            throw new IndexOutOfBoundsException(String.format(INVALID_NUMBER_OF_ASSIGNMENT_FIELDS,
                    values.length, Assignment.class.getFields().length));
        }

        String employeeID = values[FIRST_ELEMENT].strip();
        String projectID = values[SECOND_ELEMENT].strip();
        LocalDate startDate = LocalDate.parse(values[THIRD_ELEMENT].strip(), DateTimeFormatter.ofPattern(dateFormat));
        LocalDate endDate = null;
        if (!values[FORTH_ELEMENT].strip().equalsIgnoreCase(NULL)) {
            endDate = LocalDate.parse(values[FORTH_ELEMENT], DateTimeFormatter.ofPattern(dateFormat));
        }
        return new AssignmentDTO(employeeID, projectID, startDate, endDate);
    }
}
