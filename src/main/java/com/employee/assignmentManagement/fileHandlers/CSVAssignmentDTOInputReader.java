package com.employee.assignmentManagement.fileHandlers;

import com.employee.assignmentManagement.exceptions.FileException;
import com.employee.assignmentManagement.helpers.CSVAssignmentParser;
import com.employee.assignmentManagement.models.AssignmentDTO;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVAssignmentDTOInputReader implements AssignmentDTOInputReader {

    public static final String PARSING_A_LINE_EXCEPTION_IN_THE_FILE = "Parsing a line exception in the file: ";
    public static final String DATETIME_PARSE_EXCEPTION_MSG = "Parsing error in the file: The date format is WRONG," +
            " provide proper date format that is used in the file. The current is: %s\n";

    public List<AssignmentDTO> read(InputStream inputStream, boolean areHeadersPresent, String dateFormat) {
        List<AssignmentDTO> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            if (areHeadersPresent) {
                br.readLine();
            }
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    items.add(CSVAssignmentParser.parse(line, dateFormat));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new FileException(ex.getMessage());
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            throw new FileException(PARSING_A_LINE_EXCEPTION_IN_THE_FILE + ex.getMessage());
        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
            throw new FileException(String.format(DATETIME_PARSE_EXCEPTION_MSG, dateFormat) + ex.getMessage());
        }
        return items;
    }
}
