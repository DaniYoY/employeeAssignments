package com.employee.assignmentManagement.services;

import com.employee.assignmentManagement.models.Assignment;
import com.employee.assignmentManagement.models.Employee;
import com.employee.assignmentManagement.models.EmployeePairDTO;
import com.employee.assignmentManagement.models.Project;

import java.util.List;

public interface AssignmentService {
    List<Assignment> getAll();

    List<Assignment> getByEmployeeAndProject(String employeeId, String projectID);

    Assignment getById(Long id);

    Assignment update(Long id, Assignment assignment);

    void delete(Long id);

    Assignment create(Assignment assignment);

    List<Assignment> create(List<Assignment> assignments);

    List<EmployeePairDTO> findLongestRunningPairs();

    String findLongestRunningTeam();

    List<Assignment> getByEmployee(Employee employee);

    List<Assignment> getByProject(Project project);
}
