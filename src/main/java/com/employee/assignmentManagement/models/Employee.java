package com.employee.assignmentManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "employees")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private long id;

    @Column(name = "personal_number", unique = true)
    @NotBlank
    private String personalNumber;

    public Employee(long id, String personalNumber) {
        this.id = id;
        this.personalNumber = personalNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Objects.equals(personalNumber, employee.personalNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personalNumber);
    }


}
