package com.employee.assignmentManagement.helpers;

import com.employee.assignmentManagement.models.AssignmentDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CSVAssignmentParser {
    public static final int numberOfAssignmentFields = 4;

    public static final String INVALID_NUMBER_OF_ASSIGNMENT_FIELDS = "You have provided %d arguments to create " +
            "an Assignment, but only %d are required";
    public static final String COMMA = ",";
    public static final int FIRST_ELEMENT = 0;
    public static final int SECOND_ELEMENT = 1;
    public static final int THIRD_ELEMENT = 2;
    public static final int FORTH_ELEMENT = 3;
    public static final String NULL = "null";
    public static final String START_AFTER_END_MSG = "Start date is after end date. Time travelers are not permitted";

    public static AssignmentDTO parse(String line, String dateFormat) {
        String[] values = line.split(COMMA);
        if (values.length != numberOfAssignmentFields) {
            throw new IndexOutOfBoundsException(String.format(INVALID_NUMBER_OF_ASSIGNMENT_FIELDS,
                    values.length, numberOfAssignmentFields));
        }

        String employeeID = values[FIRST_ELEMENT].strip();
        String projectID = values[SECOND_ELEMENT].strip();
        LocalDate startDate = null;
        if (!values[THIRD_ELEMENT].strip().equalsIgnoreCase(NULL)) {
            startDate = LocalDate.parse(values[THIRD_ELEMENT].strip(), DateTimeFormatter.ofPattern(dateFormat));
        }
        LocalDate endDate = null;
        if (!values[FORTH_ELEMENT].strip().equalsIgnoreCase(NULL)) {
            endDate = LocalDate.parse(values[FORTH_ELEMENT].strip(), DateTimeFormatter.ofPattern(dateFormat));
        }


        if (endDate != null && (startDate == null || startDate.isAfter(endDate))){
            throw new DateTimeParseException(START_AFTER_END_MSG,
                    String.format("start: %s, end: %s",startDate.toString(), endDate.toString()) , 0);
        }
        return new AssignmentDTO(employeeID, projectID, startDate, endDate);
    }
}
