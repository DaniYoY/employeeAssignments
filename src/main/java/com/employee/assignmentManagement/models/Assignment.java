package com.employee.assignmentManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "assignments")
public class Assignment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private long id;

    @Column(name = "employee_id")
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employee;

    @Column(name = "project_id")
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Project project;
    
}
