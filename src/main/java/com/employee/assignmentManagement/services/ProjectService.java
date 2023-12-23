package com.employee.assignmentManagement.services;

import com.employee.assignmentManagement.models.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAll();

    Project getByID(Long id);

    Project getByProjectID(String id);

    Project update(Long id, Project project);

    void delete(Long id);

    void create(Project project);

    Project getOrCreate(Project project);
}
