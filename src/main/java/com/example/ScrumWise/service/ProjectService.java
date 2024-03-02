package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Department;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.ProjectStatus;

import java.util.List;

public interface ProjectService {
    Project getProject(Long id);
    List<Project> getProjectList();
    Project addProject(Project project);
    Project updateProject(Long id, Project project);
    void deleteProject(Long id);
    List<Project> getProjectsByDepartment(Long idDepartment);
    List<Project> getInProgressProjectsByDepartment(Department department);
    void updateProjectStatus(Long idProject, ProjectStatus projectStatus);
}
