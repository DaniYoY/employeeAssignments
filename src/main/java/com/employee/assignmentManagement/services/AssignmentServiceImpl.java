package com.employee.assignmentManagement.services;

import com.employee.assignmentManagement.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AssignmentServiceImpl implements AssignmentService{
    @Autowired
    private AssignmentRepository repository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    public AssignmentServiceImpl(AssignmentRepository repository, EmployeeService employeeService, ProjectService projectService) {
        this.repository = repository;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

}
