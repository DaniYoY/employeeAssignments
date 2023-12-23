package com.employee.assignmentManagement.models;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Data
public class AssignmentDTO implements Serializable {

    @Value("-1")
    private long id;

    @NotBlank(message = "Employee id is missing")
    private String employeeID;

    @NotBlank(message = "Project Id is missing")
    private String projectID;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "Start date is missing")
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    public AssignmentDTO(String employeeID, String projectID, LocalDate startDate, LocalDate endDate) {
        this.employeeID = employeeID;
        this.projectID = projectID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentDTO that = (AssignmentDTO) o;
        return Objects.equals(employeeID, that.employeeID) && Objects.equals(projectID, that.projectID) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeID, projectID, startDate, endDate);
    }

    @Override
    public String toString() {
        return employeeID + ", " + projectID + ", " + startDate + ", " + endDate;
    }
}


