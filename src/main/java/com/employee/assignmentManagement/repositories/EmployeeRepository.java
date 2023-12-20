package com.employee.assignmentManagement.repositories;

import com.employee.assignmentManagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByPersonalNumber(String personalNumber);
}
