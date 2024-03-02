package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.exception.ResourceNotFoundException;
import com.example.ScrumWise.model.entity.Epic;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.Ressource;
import com.example.ScrumWise.rest.dto.EpicDto;
import com.example.ScrumWise.rest.dto.ProjectDto;
import com.example.ScrumWise.rest.dto.RessourceDto;
import com.example.ScrumWise.service.EpicService;
import com.example.ScrumWise.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.Type;
import java.util.List;
@RestController
@CrossOrigin
public class EpicController {
    @Autowired
    private EpicService epicService ;
    @Autowired
    private ProjectService projectService ;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/epic")
    @RolesAllowed({"Manager","Consultant"})
    public Object EpicList() {
        List<Epic> epics = epicService.getEpicList();
        Type listType = new TypeToken<List<EpicDto>>() {}.getType() ;
        List<EpicDto> epicDtos = modelMapper.map(epics,listType);
        return ResponseEntity.status(HttpStatus.OK).body(epicDtos);
    }
    @GetMapping("/epic/{idEpic}")
    @RolesAllowed({"Manager","Consultant"})
    public Object Epic(@PathVariable Long idEpic ) {
        Epic epic = epicService.getEpicById(idEpic) ;
        if (epic.getIdEpic()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            EpicDto epicDto = modelMapper.map(epic, EpicDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(epicDto);
        }
    }

    @PostMapping("/epic/project/{idProject}")
    @RolesAllowed("Manager")
    public Object AddDepartment(@Validated @RequestBody EpicDto epicDto, @PathVariable Long idProject)
    {
        Epic epic = modelMapper.map(epicDto, Epic.class);
        epic.setProject(projectService.getProject(idProject));
        epic = epicService.addEpic(epic);
        epicDto = modelMapper.map(epic, EpicDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(epicDto);
    }

    @PutMapping("/epic/{idEpic}")
    @RolesAllowed("Manager")
    public Object UpdateEpic (@Validated @RequestBody EpicDto epicDto, @PathVariable Long idEpic) {
        Epic epic = modelMapper.map(epicDto, Epic.class);
        epic = epicService.updateEpic(idEpic, epic);
        epicDto = modelMapper.map(epic, EpicDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(epicDto);

    }

    @DeleteMapping("/epic/{idEpic}")
    @RolesAllowed("Manager")
    public Object DeleteEpic(@PathVariable Long idEpic) {
        epicService.deleteEpic(idEpic);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
    @GetMapping("/epic/project/{idProject}")
    @RolesAllowed({"Manager","Consultant"})
    public Object EpicByProject(@PathVariable Long idProject) {
        try{
            List<Epic> epics =  epicService.getEpicByProject(projectService.getProject(idProject)) ;
            Type listType = new TypeToken<List<EpicDto>>() {}.getType() ;
            List<EpicDto> epicDtos = modelMapper.map(epics,listType);
            return ResponseEntity.status(HttpStatus.OK).body(epicDtos);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \""+e.getMessage()+"\"}");
        }
    }
}
