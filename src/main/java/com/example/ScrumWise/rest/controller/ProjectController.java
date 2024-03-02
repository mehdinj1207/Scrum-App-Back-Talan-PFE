package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.ProjectStatus;
import com.example.ScrumWise.rest.dto.ProjectDto;
import com.example.ScrumWise.service.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@CrossOrigin
public class ProjectController {
   @Autowired
    private ProjectService projectService ;
   @Autowired
    private  ProjectStatusService projectStatusService;
    @Autowired
    private RessourceService ressourceService;
    @Autowired
    private SprintService sprintService;
    @Autowired
    private ParticipationService participationService;
    @Autowired
    private MeetService meetService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/projects")
    @RolesAllowed({"Manager","Consultant"})
    public Object ProjectList() {
        List<Project> projects = projectService.getProjectList();
        Type listType = new TypeToken<List<ProjectDto>>() {}.getType() ;
        List<ProjectDto> projectDtos = modelMapper.map(projects,listType);
        return ResponseEntity.status(HttpStatus.OK).body(projectDtos);
    }
    @GetMapping("/projects/{idProject}")
    @RolesAllowed({"Manager","Consultant"})
    public Object Project(@PathVariable Long idProject ) {
        Project project = projectService.getProject(idProject) ;
        if (project.getIdProject()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(projectDto);
        }
    }

    @PostMapping("/projects")
    @RolesAllowed("Manager")
    public Object AddProject(@Validated @RequestBody ProjectDto projectDto)
    {
        Project project = modelMapper.map(projectDto, Project.class);
        project = projectService.addProject(project);
        projectDto = modelMapper.map(project, ProjectDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectDto);
    }
    @GetMapping("/projects/department/{idDepartment}")
    @RolesAllowed({"Manager"})
    public Object ProjectListByDepartment(@PathVariable Long idDepartment) {
        List<Project> projects = projectService.getProjectsByDepartment(idDepartment);
        Type listType = new TypeToken<List<ProjectDto>>() {}.getType() ;
        List<ProjectDto> projectDtos = modelMapper.map(projects,listType);
        return ResponseEntity.status(HttpStatus.OK).body(projectDtos);
    }
    @GetMapping("/projects/consulant/{idUser}")
    @RolesAllowed({"Manager","Consultant"})
    public Object ProjectListByUser(@PathVariable Long idUser) {
        List<Project> projects = ressourceService.getProjectsByUserFromRessources(idUser);
        Type listType = new TypeToken<List<ProjectDto>>() {}.getType() ;
        List<ProjectDto> projectDtos = modelMapper.map(projects,listType);
        return ResponseEntity.status(HttpStatus.OK).body(projectDtos);
    }


    @PutMapping("/projects/{idProject}")
    @RolesAllowed("Manager")
    public Object UpdateProject (@Validated @RequestBody ProjectDto projectDto, @PathVariable Long idProject) {
        Project project = modelMapper.map(projectDto, Project.class);
        project = projectService.updateProject(idProject, project);
        projectDto = modelMapper.map(project, ProjectDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectDto);

    }

    @DeleteMapping("/projects/{idProject}")
    @RolesAllowed("Manager")
    @Transactional
    public Object DeleteProject(@PathVariable Long idProject) {

        Project projectToDelete = projectService.getProject(idProject);
        ressourceService.deleteRessourceByProject(projectToDelete);
        participationService.deleteParticipationsByProjectId(idProject);
        meetService.deleteMeetsByProject(projectToDelete);
        projectService.deleteProject(idProject);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }
    @GetMapping("/projects/in-progress/{idDepartment}")
    @RolesAllowed({"Manager","Consultant"})
    public Object ProjectInProgressList(@PathVariable Long idDepartment) {
        List<Project> projects = projectService.getInProgressProjectsByDepartment(departmentService.getDepartmentById(idDepartment));
        Type listType = new TypeToken<List<ProjectDto>>() {}.getType() ;
        List<ProjectDto> projectDtos = modelMapper.map(projects,listType);
        return ResponseEntity.status(HttpStatus.OK).body(projectDtos);
    }
    @GetMapping("/projects/status/update/{idProject}/{status}")
    @RolesAllowed({"Manager"})
    public Object updateProjectStatus(@PathVariable Long idProject, @PathVariable String status) {
        try{
            Project project = projectService.getProject(idProject);
            ProjectStatus projectStatus= projectStatusService.getProjectStatusByName(status);
            projectService.updateProjectStatus(idProject, projectStatus);
            if(status.equals("Completed")){
                sprintService.completeProjectSprints(project);
            }
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \" project "+idProject+" status became "+status+"\"}");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \""+e.getMessage()+"\"}");
        }

    }
}
