package com.employee.assignmentManagement.controllers.rest;

import com.employee.assignmentManagement.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AssignmentReSTController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/upload")
    void getAssignmentsFromFile(@RequestParam("file") MultipartFile multipartFile){

    }
}
