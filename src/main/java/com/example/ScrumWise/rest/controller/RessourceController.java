package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.exception.ResourceNotFoundException;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.Ressource;
import com.example.ScrumWise.model.entity.User;
import com.example.ScrumWise.rest.dto.RessourceDto;
import com.example.ScrumWise.service.ProjectService;
import com.example.ScrumWise.service.RessourceRoleService;
import com.example.ScrumWise.service.RessourceService;
import com.example.ScrumWise.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.Type;
import java.util.List;
@CrossOrigin(maxAge = 3600)
@Controller
public class RessourceController {
    @Autowired
    private RessourceService ressourceService ;
    @Autowired
    private RessourceRoleService ressourceRoleService ;
    @Autowired
    private UserService userService ;
    @Autowired
    private ProjectService projectService ;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/ressources")
    @RolesAllowed({"Manager","Consultant"})
    public Object RessourceList() {
        List<Ressource> ressources = ressourceService.getRessourceList();
        Type listType = new TypeToken<List<RessourceDto>>() {}.getType() ;
        List<RessourceDto> ressourceDtos = modelMapper.map(ressources,listType);
        return ResponseEntity.status(HttpStatus.OK).body(ressourceDtos);
    }
    @GetMapping("/ressources/{idRessource}")
    @RolesAllowed({"Manager","Consultant"})
    public Object Ressource(@PathVariable Long idRessource ) {
        Ressource ressource = ressourceService.getRessourceById(idRessource) ;
        if (ressource.getIdRessource()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            RessourceDto ressourceDto = modelMapper.map(ressource, RessourceDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(ressourceDto);
        }
    }

    @GetMapping("/ressources/project/{idProject}/user/{idUser}")
    @RolesAllowed({"Manager","Consultant"})
    public Object RessourceByProjectAndUser(@PathVariable Long idProject, @PathVariable Long idUser) {
        try {
            Ressource ressource = ressourceService.getRessourceByUserAndProject(
                    userService.getUserById(idUser),
                    projectService.getProject(idProject)
            );
            RessourceDto ressourceDto = modelMapper.map(ressource, RessourceDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(ressourceDto);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/ressources/project/{idProject}")
    @RolesAllowed({"Manager","Consultant"})
    public Object ProjectRessourceList(@PathVariable Long idProject) {
        try {
            List<Ressource> ressources = ressourceService.getRessourceByProject(projectService.getProject(idProject));
            Type listType = new TypeToken<List<RessourceDto>>() {}.getType();
            List<RessourceDto> ressourceDtos = modelMapper.map(ressources, listType);
            return ResponseEntity.status(HttpStatus.OK).body(ressourceDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No resources found for project with id: " + idProject);
        }
    }

    @PostMapping("/ressources/{idProject}/{idUser}/{idRole}")
    @RolesAllowed("Manager")
    public Object AddRessource(@PathVariable Long idProject,
                               @PathVariable Long idUser,@PathVariable Long idRole)
    {
        Ressource ressource = new Ressource();
        ressource.setRole(ressourceRoleService.getRessourceRoleById(idRole));
        ressource.setUser(userService.getUserById(idUser));
        ressource.setProject(projectService.getProject(idProject));
        ressource = ressourceService.addRessource(ressource);
        RessourceDto ressourceDto = modelMapper.map(ressource, RessourceDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ressourceDto);
    }
    // Edit the role of the user in the project
    @PutMapping("/ressources/{idProject}/{idUser}/{idRole}")
    @RolesAllowed("Manager")
    public Object UpdateRessource (@PathVariable Long idProject, @PathVariable Long idUser,
                                   @PathVariable Long idRole) {
        User user= userService.getUserById(idUser);
        Project project = projectService.getProject(idProject);
        Ressource ressource = ressourceService.getRessourceByUserAndProject(user, project);
        ressource.setRole(ressourceRoleService.getRessourceRoleById(idRole));
        ressource = ressourceService.updateRessource(ressource.getIdRessource(), ressource);
        RessourceDto ressourceDto = modelMapper.map(ressource, RessourceDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ressourceDto);
    }

    @DeleteMapping("/ressources/{idRessource}")
    @RolesAllowed("Manager")
    public Object DeleteRessource(@PathVariable Long idRessource) {
        ressourceService.deleteRessource(idRessource);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
    @DeleteMapping("/ressources/{idProject}/{idUser}")
    @RolesAllowed("Manager")
    public Object DeleteRessource(@PathVariable Long idProject, @PathVariable Long idUser) {
        ressourceService.deleteRessourceByUserAndProject(userService.getUserById(idUser), projectService.getProject(idProject));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/ressources/byUser/{idUser}")
    @RolesAllowed({"Manager","Consultant"})
    public Object GetRessourcesByUser(@PathVariable Long idUser) {
        try {
            User user = userService.getUserById(idUser);
            List<Ressource> ressources = ressourceService.getRessourceByUser(user);
            Type listType = new TypeToken<List<RessourceDto>>() {}.getType();
            List<RessourceDto> ressourceDtos = modelMapper.map(ressources, listType);
            return ResponseEntity.status(HttpStatus.OK).body(ressourceDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No resources found for this user "+ idUser );
        }
    }
}
