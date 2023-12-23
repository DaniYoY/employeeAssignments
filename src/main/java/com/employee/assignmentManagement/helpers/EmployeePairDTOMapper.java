package com.employee.assignmentManagement.helpers;

import com.employee.assignmentManagement.models.EmployeePairDTO;

public class EmployeePairDTOMapper {
    public static EmployeePairDTO toEmployeePair(Object[]o){
        return new EmployeePairDTO((long) o[0], (long) o[1], (long) o[2], (long) o[3]);
    }
}
