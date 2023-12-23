package com.employee.assignmentManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
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

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
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

    public void setId(long id) {
        this.id = id;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isOverLapping(Assignment a) {
        LocalDate OfThisEndDate = this.endDate == null ? LocalDate.now() : this.endDate;
        LocalDate OfAEndDate = a.endDate == null ? LocalDate.now() : a.endDate;
        return !(OfThisEndDate.isBefore(a.startDate) || this.startDate.isAfter(OfAEndDate));
    }

    public boolean merges(Assignment a) {
        if (this.employee.equals(a.employee)
                && this.project.equals(a.project)
                && isOverLapping(a)) {

            this.startDate = this.startDate.isBefore(a.startDate) ? this.startDate : a.startDate;
            if (this.endDate == null || a.endDate == null) {
                this.endDate = null;
            } else {
                this.endDate = this.endDate.isAfter(a.endDate) ? this.endDate : a.endDate;
            }
            return true;
        }
        return false;
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
