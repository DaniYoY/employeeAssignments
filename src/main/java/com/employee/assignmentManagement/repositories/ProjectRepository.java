package com.employee.assignmentManagement.repositories;

import com.employee.assignmentManagement.models.Employee;
import com.employee.assignmentManagement.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
Optional<Project> findByProjectNumber(String projectNumber);

}
