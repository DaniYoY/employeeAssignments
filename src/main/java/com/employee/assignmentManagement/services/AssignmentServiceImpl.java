package com.employee.assignmentManagement.services;

import com.employee.assignmentManagement.models.*;
import com.employee.assignmentManagement.repositories.AssignmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private AssignmentRepository repository;
    private EmployeeService employeeService;
    private ProjectService projectService;

    @Autowired
    public AssignmentServiceImpl(AssignmentRepository repository, EmployeeService employeeService, ProjectService projectService) {
        this.repository = repository;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @Override
    public List<Assignment> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Assignment> getByEmployeeAndProject(String employeeId, String projectId) {
        Employee employee = employeeService.getByPersonalID(employeeId);
        Project project = projectService.getByProjectID(projectId);
        return repository.findByEmployeeAndProject(employee, project);
    }

    @Override
    public Assignment getById(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Assignment update(Long id, Assignment assignment) {
        Assignment updatable = getById(id);
        updatable.setEmployee(employeeService.getByPersonalID(assignment.getEmployee().getPersonalNumber()));
        updatable.setProject(projectService.getByProjectID(assignment.getProject().getProjectNumber()));
        updatable.setStartDate(assignment.getStartDate());
        updatable.setEndDate(assignment.getEndDate());
        return repository.save(updatable);
    }

    public void delete(Long id) {
        Assignment a = getById(id);
        repository.deleteById(id);
    }

    @Override
    public Assignment create(Assignment assignment) {
        return create(assignment, getByEmployeeAndProject(assignment.getEmployee().getPersonalNumber(),
                assignment.getProject().getProjectNumber()));
    }

    @Override
    public List<Assignment> create(List<Assignment> assignments) {
        List<Assignment> all = getAll();
        List<Assignment> result = new ArrayList<>();
        for (Assignment a : assignments) {
            result.add(create(a, all));
        }
        return result;
    }


    @Override
    public List<EmployeePairDTO> findLongestRunningPair(){
        return repository.findLongestRunningPairs();
    }

    @Override
    public String findLongestRunningTeam() {
        List<EmployeePairDTO> pairs = repository.findLongestRunningPairs();
        Map<TeamDTO, TeamAssignmentDTO> teams = new HashMap<>();
        for (EmployeePairDTO pair: pairs) {
            TeamDTO t = new TeamDTO(employeeService.getByID(pair.getFirstEmpId()).getPersonalNumber(),
                            employeeService.getByID(pair.getSecondEmplId()).getPersonalNumber());
            teams.putIfAbsent(t,new TeamAssignmentDTO(new HashMap<>(), 0L));
            String project = projectService.getByID(pair.getProjectId()).getProjectNumber();

            if (teams.get(t).getProject().containsKey(project)) {
                long currentDuration = teams.get(t).getProject().get(project);
                teams.get(t).getProject().put(project, currentDuration + pair.getDuration());
            }else {
                teams.get(t).getProject().putIfAbsent(project, pair.getDuration());
            }
            teams.get(t).setTotalDuration(teams.get(t).getTotalDuration() + pair.getDuration());
        }
        Map.Entry<TeamDTO,TeamAssignmentDTO> longestRunning = Collections.max(teams.entrySet(),
                Comparator.comparing((Map.Entry<TeamDTO, TeamAssignmentDTO> e) -> e.getValue().getTotalDuration()));
        StringBuilder sb = new StringBuilder();
        sb.append(longestRunning.getKey().toString()).append(',').append(longestRunning.getValue().getTotalDuration())
                .append(System.lineSeparator());
        for (Map.Entry<String,Long> projectDuration: longestRunning.getValue().getProject().entrySet()) {
                sb.append(projectDuration.getKey())
                        .append(',')
                        .append(projectDuration.getValue())
                        .append(System.lineSeparator());
        }
        return sb.toString();
    }
    private Assignment create(Assignment assignment, List<Assignment> assignmentList) {
        assignment.setEmployee(employeeService.getByPersonalID(assignment.getEmployee().getPersonalNumber()));
        assignment.setProject(projectService.getByProjectID(assignment.getProject().getProjectNumber()));
        boolean isMerged = false;
        long id = 0;
        for (Assignment a : assignmentList) {
            if (assignment.merges(a)) {
                isMerged = true;
                id = a.getId();
                break;
            }
        }

        if (isMerged) {
            assignment.setId(id);
        }
        return repository.save(assignment);
    }

}
