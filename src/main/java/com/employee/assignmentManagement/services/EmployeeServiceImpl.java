package com.employee.assignmentManagement.services;

import com.employee.assignmentManagement.exceptions.DuplicateEntityException;
import com.employee.assignmentManagement.models.Employee;
import com.employee.assignmentManagement.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    public Employee getByID(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Employee getByPersonalID(String id) {
        return repository.findByPersonalNumber(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee emp = getByID(id);
        emp.setPersonalNumber(employee.getPersonalNumber());
        return repository.save(emp);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void create(Employee employee) {
        try {
            Employee emp = getByPersonalID(employee.getPersonalNumber());
            throw new DuplicateEntityException(employee.getClass().getSimpleName());
        } catch (EntityNotFoundException ex) {
            repository.save(employee);
        }
    }

    @Override
    public Employee getOrCreate(Employee employee){
        try{
            create(employee);
        }catch (DuplicateEntityException ex){
            System.out.println("Already is system");
        }
        return getByPersonalID(employee.getPersonalNumber());
    }
}
