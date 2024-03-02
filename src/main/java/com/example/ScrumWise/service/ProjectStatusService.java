package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.ProjectStatus;
import com.example.ScrumWise.model.entity.User;

import java.util.List;

public interface ProjectStatusService {

    List<ProjectStatus> getProjectStatuses();
    ProjectStatus getProjectStatusByName(String statusName);



}
