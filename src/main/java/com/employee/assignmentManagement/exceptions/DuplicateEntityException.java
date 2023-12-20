package com.employee.assignmentManagement.exceptions;

public class DuplicateEntityException extends RuntimeException{
    public DuplicateEntityException(String className) {
        super("You cannot create such: " + className);
    }
}
