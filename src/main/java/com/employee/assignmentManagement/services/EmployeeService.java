package com.employee.assignmentManagement.services;

import com.employee.assignmentManagement.models.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();

    Employee getByID(Long id);

    Employee getByPersonalID(String id);

    Employee update(Long id, Employee employee);

    void delete(Long id);

    void create(Employee employee);
}
