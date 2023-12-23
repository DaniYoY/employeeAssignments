package com.employee.assignmentManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmployeePairDTO {

    long firstEmpId;
    long secondEmplId;
    long projectId;
    long duration;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeePairDTO that)) return false;
        return Objects.equals(getFirstEmpId(), that.getFirstEmpId()) && Objects.equals(getSecondEmplId(), that.getSecondEmplId())
                || Objects.equals(getFirstEmpId(), that.getSecondEmplId()) && Objects.equals(getSecondEmplId(), that.getFirstEmpId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstEmpId(), getSecondEmplId());
    }
}
