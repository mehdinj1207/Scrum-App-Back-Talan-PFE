package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.Department;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.ProjectStatus;
import com.example.ScrumWise.repository.ProjectRepository;
import com.example.ScrumWise.repository.ProjectStatusRepository;
import com.example.ScrumWise.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProjectServiceImp implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository ;
    @Autowired
    private ProjectStatusRepository projectStatusRepository ;
    @Override
    public Project getProject(Long id) {
        try{
            return projectRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Projet not found!");
            return new Project();
        }
    }

    @Override
    public List<Project> getProjectList() {
        return projectRepository.findAll();
    }

    @Override
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project project) {
        getProject(id);
        project.setIdProject(id);
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> getProjectsByDepartment(Long idDepartment){
        return  projectRepository.findByDepartmentIdDepartment(idDepartment);
    }
    @Override
    public List<Project> getInProgressProjectsByDepartment(Department department) {
        return projectRepository.findByProjectStatusAndDepartment(projectStatusRepository.findByStatusName("In Progress"), department);
    }
    @Override
    public void updateProjectStatus(Long idProject, ProjectStatus projectStatus){
        Project project= projectRepository.findById(idProject).get();
        project.setProjectStatus(projectStatus);
        projectRepository.save(project);
    }
}
