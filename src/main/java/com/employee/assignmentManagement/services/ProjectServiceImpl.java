package com.employee.assignmentManagement.services;

import com.employee.assignmentManagement.exceptions.DuplicateEntityException;
import com.employee.assignmentManagement.models.Employee;
import com.employee.assignmentManagement.models.Project;
import com.employee.assignmentManagement.repositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository repository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> getAll() {
        return repository.findAll();
    }

    @Override
    public Project getByID(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Project getByProjectID(String id) {
        return repository.findByProjectNumber(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Project update(Long id, Project project) {
        Project p = getByID(id);
        p.setProjectNumber(project.getProjectNumber());
        return repository.save(p);
    }

    @Override
    public void delete(Long id) {
    repository.deleteById(id);
    }

    @Override
    public void create(Project project) {
        try{
            Project p = getByProjectID(project.getProjectNumber());
            throw new DuplicateEntityException(project.getClass().getSimpleName());
        }catch (EntityNotFoundException ex) {
            repository.save(project);
        }
    }
}
