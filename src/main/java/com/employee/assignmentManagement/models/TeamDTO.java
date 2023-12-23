package com.employee.assignmentManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamDTO {
    String employee1;
    String employee2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamDTO teamDTO)) return false;
        return (Objects.equals(getEmployee1(), teamDTO.getEmployee1()) && Objects.equals(getEmployee2(), teamDTO.getEmployee2()))
        || (Objects.equals(getEmployee1(), teamDTO.getEmployee2()) && Objects.equals(getEmployee2(), teamDTO.getEmployee1()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployee1(), getEmployee2());
    }

    @Override
    public String toString() {
        return employee1 + ',' + employee2;
    }
}
