package com.example.ScrumWise.rest.controller;


import com.example.ScrumWise.model.entity.ProjectStatus;
import com.example.ScrumWise.service.ProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
public class ProjectStatusController {

    @Autowired
    private ProjectStatusService projectStatusService;


    //Get all Users
    @GetMapping("/projectStatuses")
    @RolesAllowed({"Manager","Consultant"})
    public List<ProjectStatus> getAllProjectStatuses(){
        return projectStatusService.getProjectStatuses();
    }



}
