package com.employee.assignmentManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "assignments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private long id;

    @Column(name = "employee_id")
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Employee employee;

    @Column(name = "project_id")
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Project project;

    @Column(name = "start_date")
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    public Assignment(Employee employee, Project project, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Assignment(long id, Employee employee, Project project, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.employee = employee;
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return id == that.id && Objects.equals(employee, that.employee) && Objects.equals(project, that.project) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employee, project, startDate, endDate);
    }
}
