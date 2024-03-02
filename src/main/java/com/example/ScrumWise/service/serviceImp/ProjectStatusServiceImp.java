package com.example.ScrumWise.service.serviceImp;


import com.example.ScrumWise.model.entity.ProjectStatus;
import com.example.ScrumWise.repository.ProjectStatusRepository;
import com.example.ScrumWise.repository.UserRepository;
import com.example.ScrumWise.service.ProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectStatusServiceImp implements ProjectStatusService {

    @Autowired
    ProjectStatusRepository projectStatusRepository;


    @Override
    public List<ProjectStatus> getProjectStatuses() {
        return projectStatusRepository.findAll();
    }
    @Override
    public ProjectStatus getProjectStatusByName(String statusName){
        return projectStatusRepository.findByStatusName(statusName);
    }
}
