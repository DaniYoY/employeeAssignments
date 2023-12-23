package com.employee.assignmentManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamAssignmentDTO {
    private Map<String, Long> project;
    private Long totalDuration;
}

