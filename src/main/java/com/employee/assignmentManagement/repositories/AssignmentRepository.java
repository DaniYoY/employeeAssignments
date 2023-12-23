package com.employee.assignmentManagement.repositories;

import com.employee.assignmentManagement.helpers.EmployeePairDTOMapper;
import com.employee.assignmentManagement.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

@Query(value = "SELECT record.employee1, record.employee2, record.project_id, SUM(days)as days " +
        "FROM (SELECT team.employeeid1 employee1, team.employeeid2 employee2, team.project_id project_id, " +
        "team.start_date, team.end_date, team.days days " +
            "FROM (SELECT a1.employee_id employeeid1, a2.employee_id employeeid2, a1.project_id, " +

            "CASE WHEN a1.start_date > a2.start_date THEN a1.start_date ELSE a2.start_date END start_date, " +

            "CASE WHEN COALESCE(a1.end_date, CURRENT_DATE) < COALESCE(a2.end_date, CURRENT_DATE) THEN " +
            "COALESCE(a1.end_date, CURRENT_DATE) ELSE COALESCE(a2.end_date, CURRENT_DATE) END end_date, " +

            "(CASE WHEN COALESCE(a1.end_date, CURRENT_DATE) < COALESCE(a2.end_date, CURRENT_DATE) " +
            "THEN COALESCE(a1.end_date, CURRENT_DATE) ELSE COALESCE(a2.end_date, CURRENT_DATE) END - " +
            "CASE WHEN a1.start_date > a2.start_date THEN a1.start_date ELSE a2.start_date END + 1) days " +

                "FROM assignments a1 " +
                "INNER JOIN assignments a2 " +
                "ON a2.project_id = a1.project_id AND a2.employee_id > a1.employee_id " +

            "WHERE CASE WHEN a1.start_date > a2.start_date THEN a1.start_date ELSE a2.start_date END " +
            "< " +
            "CASE WHEN COALESCE(a1.end_date, CURRENT_DATE) < COALESCE(a2.end_date, CURRENT_DATE) " +
            "THEN COALESCE(a1.end_date, CURRENT_DATE) ELSE COALESCE(a2.end_date, CURRENT_DATE) END)" +
            " team) record " +

        "GROUP BY employee1, employee2, project_id " +
        "ORDER BY days DESC",
        nativeQuery = true)
    List<Object[]> findLongestRunningTeamsAsObj();

    default List<EmployeePairDTO> findLongestRunningPairs(){
        return findLongestRunningTeamsAsObj().stream()
                .map(EmployeePairDTOMapper::toEmployeePair)
                .collect(Collectors.toList());
    }

    List<Assignment> findByEmployeeAndProject(Employee employee, Project project);
}

