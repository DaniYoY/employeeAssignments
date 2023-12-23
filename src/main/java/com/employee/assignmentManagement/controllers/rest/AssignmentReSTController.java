package com.employee.assignmentManagement.controllers.rest;

import com.employee.assignmentManagement.exceptions.FileException;
import com.employee.assignmentManagement.fileHandlers.AssignmentDTOInputReader;
import com.employee.assignmentManagement.helpers.AssignmentDTOMapper;
import com.employee.assignmentManagement.models.*;
import com.employee.assignmentManagement.services.AssignmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/assignments")
public class AssignmentReSTController {

    @Autowired
    private final AssignmentService assignmentService;

    @Autowired
    private final AssignmentDTOInputReader reader;

    public AssignmentReSTController(AssignmentService assignmentService, AssignmentDTOInputReader reader) {
        this.assignmentService = assignmentService;
        this.reader = reader;
    }

//    TODO consumes = {"multipart/form-data"}
    @PostMapping(value = "/upload")
    ResponseEntity<String> uploadAssignmentsFromFile(@RequestParam("file") @Valid @NotNull MultipartFile multipartFile,
                                             @RequestParam("areHeaders") @Valid @NotBlank String areHeadersStr,
                                             @RequestParam("dateFormat") @NotNull String dateFormat) throws IOException {
        try {
            boolean areHeaders;
            if (areHeadersStr.equalsIgnoreCase("Y") || areHeadersStr.equalsIgnoreCase("yes")
                    || areHeadersStr.equalsIgnoreCase("true")){
                areHeaders = true;
            } else if (areHeadersStr.equalsIgnoreCase("n") || areHeadersStr.equalsIgnoreCase("no")
                    || areHeadersStr.equalsIgnoreCase("false")) {
                areHeaders = false;
            }else {
                return ResponseEntity.badRequest().body("areHeaders must be Y/YES/TRUE/N/NO/FALSE");
            }
            if(dateFormat.isEmpty()){
                dateFormat = "yyyy-MM-d";
            }

            List<AssignmentDTO> assignmentDTOS = reader.read(multipartFile.getInputStream(),
                    areHeaders,
                    dateFormat);
            List<Assignment> result = assignmentDTOS.stream().map(AssignmentDTOMapper::setAssignment).toList();
            assignmentService.create(result);
            return ResponseEntity.ok("Successful upload");
        }catch (FileException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/longestpairs")
    ResponseEntity<List<EmployeePairDTO>> getLongestRunningPairColleagues(){
        return ResponseEntity.ok().body(assignmentService.findLongestRunningPairs());
    }
    @GetMapping("/longestrunningteam")
    ResponseEntity<String> getLongestRunningPairColleaguesWithDates(){
        return ResponseEntity.ok().body(assignmentService.findLongestRunningTeam());
    }
    @GetMapping
    ResponseEntity<List<AssignmentDTO>> getAll(){
        List<AssignmentDTO> result = new ArrayList<>();
        for (Assignment a : assignmentService.getAll()){
            result.add(AssignmentDTOMapper.setToDTO(a));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = {"/{employeeId}/{projectID}/all", "/{employeeId}/{projectID}"} )
    ResponseEntity<List<AssignmentDTO>> getAllByEmployeeAndProject(@PathVariable("employeeId") String employeeId,
                                               @PathVariable("projectID") String projectId){
        List<AssignmentDTO> result = new ArrayList<>();
        for (Assignment a : assignmentService.getByEmployeeAndProject(employeeId, projectId)){
            result.add(AssignmentDTOMapper.setToDTO(a));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{employeeId}/{projectID}/{id}")
    ResponseEntity<AssignmentDTO> getByEmployeeAndProject(@PathVariable("employeeId") String employeeId,
                                                          @PathVariable("projectID") String projectId,
                                                          @PathVariable("id") Long id){
        try {
            AssignmentDTO assignmentDTO = AssignmentDTOMapper.setToDTO(assignmentService.getById(id));
            if (assignmentDTO.getEmployeeID().equals(employeeId) && assignmentDTO.getProjectID().equals(projectId)) {
                return ResponseEntity.ok(assignmentDTO);
            }
        }catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AssignmentDTO());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new AssignmentDTO());
    }
    @GetMapping("/{id}")
    ResponseEntity<AssignmentDTO> getById(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok(AssignmentDTOMapper.setToDTO(assignmentService.getById(id)));
        }catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AssignmentDTO());
        }
    }
    @PostMapping
    ResponseEntity<AssignmentDTO> createOne(@Valid @RequestBody AssignmentDTO dto){
        Assignment assignment = AssignmentDTOMapper.setAssignment(dto);;
        return ResponseEntity.ok(AssignmentDTOMapper.setToDTO(assignmentService.create(assignment)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteOne(@PathVariable long id){
        try {
            assignmentService.delete(id);
            return ResponseEntity.ok("Success");
        }catch (EntityNotFoundException ex){
            return ResponseEntity.badRequest().body("Fail");
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<String> createOne(@PathVariable long id, @Valid @RequestBody AssignmentDTO dto){
        try {
            assignmentService.update(id, AssignmentDTOMapper.setAssignment(dto));
            return ResponseEntity.ok("Success");
        }catch (EntityNotFoundException ex){
            return ResponseEntity.badRequest().body("Fail");
        }
    }
}
