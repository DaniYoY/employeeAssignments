package com.employee.assignmentManagement.helpers;

import com.employee.assignmentManagement.models.Assignment;
import com.employee.assignmentManagement.models.AssignmentDTO;
import com.employee.assignmentManagement.models.Employee;
import com.employee.assignmentManagement.models.Project;

public class AssignmentDTOMapper {
    public  static Assignment setAssignment(AssignmentDTO dto){
        Assignment assignment = new Assignment();
        Employee employee = new Employee();
        Project project = new Project();
        employee.setPersonalNumber(dto.getEmployeeID());
        project.setProjectNumber(dto.getProjectID());
        assignment.setId(dto.getId());
        assignment.setEmployee(employee);
        assignment.setProject(project);
        assignment.setStartDate(dto.getStartDate());
        assignment.setEndDate(dto.getEndDate());
        return assignment;
    }
    public  static AssignmentDTO setToDTO (Assignment a){
        AssignmentDTO dto = new AssignmentDTO();
        dto.setId(a.getId());
        dto.setEmployeeID(a.getEmployee().getPersonalNumber());
        dto.setProjectID(a.getProject().getProjectNumber());
        dto.setStartDate(a.getStartDate());
        dto.setEndDate(a.getEndDate());
        return dto;
    }
}
