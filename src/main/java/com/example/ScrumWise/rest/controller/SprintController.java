package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.Sprint;
import com.example.ScrumWise.model.entity.SprintStatus;
import com.example.ScrumWise.model.entity.Ticket;
import com.example.ScrumWise.rest.dto.SprintDto;
import com.example.ScrumWise.service.ProjectService;
import com.example.ScrumWise.service.SprintService;
import com.example.ScrumWise.service.TicketService;
import com.example.ScrumWise.service.WorkflowItemService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class SprintController {

    @Autowired
    SprintService sprintService;

    @Autowired
    ProjectService projectService;
    @Autowired
    WorkflowItemService workflowItemService;
    @Autowired
    TicketService ticketService;

    @Autowired
    private ModelMapper modelMapper ;

    //Get Sprint By Id
    @GetMapping("/sprints/{idSprint}")
    @RolesAllowed({"Manager","Consultant"})
    public Object getSprint(@PathVariable Long idSprint) {

        Sprint sprint = sprintService.getSprint(idSprint);

        if (sprint.getIdSprint()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            SprintDto sprintDto = modelMapper.map(sprint, SprintDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(sprintDto);
        }
    }



    //Get All Sprint by Project
    @GetMapping("/sprints/project/{idProject}")
    @RolesAllowed({"Manager","Consultant"})
    public Object sprintsByProjectList(@PathVariable Long idProject) {

        List<Sprint> sprints = sprintService.getSprintsByProject(idProject);

        Type listType = new TypeToken<List<SprintDto>>() {}.getType() ;
        List<SprintDto> sprintDtos = modelMapper.map(sprints,listType);
        return ResponseEntity.status(HttpStatus.OK).body(sprintDtos);

    }

    //Add a new Sprint to a Project
    @PostMapping("/sprints/addSprint/{idProject}")
    @RolesAllowed({"Manager"})
    public Object addSprint(@RequestBody Sprint sprint, @PathVariable Long idProject){
        try{
            sprint.setSprintStatus(sprintService.getSprintStatusByName("Created"));
            sprint.setProject(projectService.getProject(idProject));
            Sprint newSprint = sprintService.addSprint(sprint);
            workflowItemService.getDefaultWorkflow(newSprint);
            return ResponseEntity.status(HttpStatus.OK).body(newSprint);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }


    //Update a Sprint
    @PutMapping("/sprints/updateSprint/{idSprint}")
    @RolesAllowed({"Manager"})
    public Object updateSprint(@RequestBody Sprint sprint,@PathVariable Long idSprint){

        Sprint newSprint = sprintService.updateSprint(idSprint,sprint);
        return ResponseEntity.status(HttpStatus.OK).body(newSprint);

    }
    @GetMapping("/sprints/current/project/{idProject}")
    @RolesAllowed({"Manager","Consultant"})
    public Object getCurrentSprintByProject(@PathVariable Long idProject) {
        Long id= sprintService.getIdBySprintStatusInProgressAndProjectId(idProject);
        if(id!=null){
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    @GetMapping("/sprints/complete/{idSprint}")
    @RolesAllowed({"Manager"})
    public Object completeSprint(@PathVariable Long idSprint){

        Sprint sprint=sprintService.getSprint(idSprint);
        List<Sprint> sprintsCreated= sprintService.getByProjectAndSprintStatus_Name(sprint.getProject(),"Created");
        System.out.println("length sprint created: "+sprintsCreated.size());
        if(!sprintsCreated.isEmpty()){
            ticketService.moveTicketsNonCompletedToNextSprint(idSprint,sprintsCreated.get(0));
        }
        else{
            Sprint newSprint = new Sprint();
            newSprint.setProject(sprint.getProject());
            newSprint.setStartDate(LocalDate.now());
            newSprint.setEndDate(newSprint.getStartDate().plusDays(14));
            newSprint.setName("New Sprint");
            newSprint.setSprintStatus(sprintService.getSprintStatusByName("Created"));
            newSprint.setObjective("Deliver a set of defined features.");
            Sprint sprint1=sprintService.addSprint(newSprint);
            workflowItemService.getDefaultWorkflow(sprint1);
            ticketService.moveTicketsNonCompletedToNextSprint(idSprint,sprint1);

        }
        Sprint completedSprint = sprintService.completeSprint(idSprint);
        return ResponseEntity.status(HttpStatus.OK).body(completedSprint);
    }

    @GetMapping("/sprints/start/{idSprint}")
    @RolesAllowed({"Manager"})
    public Object startSprint(@PathVariable Long idSprint){
        Sprint newSprint = sprintService.startSprint(idSprint);
        return ResponseEntity.status(HttpStatus.OK).body(newSprint);
    }


    //Delete Sprint
    @DeleteMapping("/sprints/{idSprint}")
    @RolesAllowed("Manager")
    @Transactional
    public Object DeleteProject(@PathVariable Long idSprint) {
        sprintService.deleteSprint(idSprint);
        return ResponseEntity.ok("Sprint Deleted Successfully");

    }

    //Get all sprintStatuses
    @GetMapping("/sprints/sprintStatuses")
    @RolesAllowed({"Manager","Consultant"})
    public Object getSprintStatuses() {

        List<SprintStatus> sprintStatuses = sprintService.getSprintStatuses();
        return ResponseEntity.status(HttpStatus.OK).body(sprintStatuses);
    }
    @GetMapping("/sprints/last/project/{idProject}")
    @RolesAllowed({"Manager","Consultant"})
    public Object getLastSprintOfProject(@PathVariable Long idProject ) {
        Object sprint= sprintService.getLastSprintByProject(projectService.getProject(idProject));
        if (sprintService.getLastSprintByProject(projectService.getProject(idProject))==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            return ResponseEntity.status(HttpStatus.OK).body(sprint);
        }
    }




}
