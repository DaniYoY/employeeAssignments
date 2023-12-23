package com.employee.assignmentManagement.controllers.rest;

import com.employee.assignmentManagement.models.Assignment;
import com.employee.assignmentManagement.models.Employee;
import com.employee.assignmentManagement.services.AssignmentService;
import com.employee.assignmentManagement.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeReSTController {
    private final EmployeeService service;
    private final AssignmentService assignmentService;
    @Autowired
    public EmployeeReSTController(EmployeeService service, AssignmentService assignmentService) {
        this.service = service;
        this.assignmentService = assignmentService;
    }

    @GetMapping
    ResponseEntity<List<String>> getAll(){
        try{
            return ResponseEntity.ok(service.getAll().stream().map(Employee::getPersonalNumber).toList());
        }catch (RuntimeException ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @GetMapping(value = "/{personalid}")
    ResponseEntity<String> getByPersonalId(@PathVariable @Valid String personalid){
        try{
            return ResponseEntity.ok(service.getByPersonalID(personalid).getPersonalNumber());
        }catch (EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }catch (RuntimeException ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @PostMapping
    ResponseEntity<String> createWithString(@RequestBody String personalid){
        try{
            Employee employee = new Employee();
            employee.setPersonalNumber(personalid);
            return ResponseEntity.ok(service.getOrCreate(employee).getPersonalNumber());
        }catch (EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }catch (RuntimeException ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @PutMapping(value = "/{personalid}")
    ResponseEntity<String> updateByPersonalId(@PathVariable String personalid, @RequestBody String newPersonalId){
        try{
            Employee employee = service.getByPersonalID(personalid);
            employee.setPersonalNumber(personalid);
            return ResponseEntity.ok(service.update(employee.getId(), employee).getPersonalNumber());
        }catch (EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }catch (RuntimeException ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @DeleteMapping(value = "/{personalid}")
    ResponseEntity<String> deleteByPersonalId(@PathVariable String personalid){
        try{
            Employee employee = service.getByPersonalID(personalid);
            List<Assignment> assignments = assignmentService.getByEmployee(employee);
            assignments.forEach(assignment -> assignmentService.delete(assignment.getId()));
            service.delete(employee.getId());
            return ResponseEntity.ok("Success");
        }catch (EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }catch (RuntimeException ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }
}
