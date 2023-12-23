package com.employee.assignmentManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "projects")
@PrimaryKeyJoinColumn(name = "id")
//@Data
//@AllArgsConstructor
@NoArgsConstructor
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private long id;

    @Column(name = "project_number", unique = true)
    @NotBlank
    private String projectNumber;

    public Project(long id, String projectNumber) {
        this.id = id;
        this.projectNumber = projectNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id && Objects.equals(projectNumber, project.projectNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectNumber);
    }
}
