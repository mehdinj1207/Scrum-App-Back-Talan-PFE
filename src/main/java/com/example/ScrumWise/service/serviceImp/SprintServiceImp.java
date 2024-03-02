package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.Sprint;
import com.example.ScrumWise.model.entity.SprintStatus;
import com.example.ScrumWise.repository.ProjectRepository;
import com.example.ScrumWise.repository.SprintRepository;
import com.example.ScrumWise.repository.SprintStatusRepository;
import com.example.ScrumWise.service.SprintService;
import com.example.ScrumWise.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SprintServiceImp implements SprintService {

    @Autowired
    private SprintRepository sprintRepository;
    @Autowired
    private SprintStatusRepository sprintStatusRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TicketService ticketService;

    @Override
    public Sprint getSprint(Long id) {
        try{
            return sprintRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Sprint not found!");
            return new Sprint();
        }
    }

    @Override
    public List<Sprint> getSprintsByProject(Long idProject) {
        Project project = projectRepository.findById(idProject).get();
        return sprintRepository.findSprintByProject(project);
    }

    @Override
    public Sprint addSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    @Override
    public Sprint updateSprint(Long id, Sprint sprint) {
        getSprint(id);
        sprint.setIdSprint(id);
        return sprintRepository.save(sprint);
    }

    @Override
    public void deleteSprint(Long id) {
        sprintRepository.deleteById(id);
    }

    @Override
    public void deleteSprintByProject(Project project) {
        List<Sprint> sprints = sprintRepository.findSprintByProject(project);
        for(int i = 0 ;i<sprints.size();i++){

            //Delete all tickets for every sprint then delete the sprint
            //ticketService.deleteTicketBySprint(sprints.get(i));
            sprintRepository.delete(sprints.get(i));
        }
    }
    @Override
    public Sprint getLastSprintByProject(Project project){
        try{
            return sprintRepository.findTopByProjectOrderByEndDateDesc(project);
        }catch (NoSuchElementException e) {
            System.out.println("No sprint found!");
            return null;
        }
    }
    @Override
    public List<SprintStatus> getSprintStatuses() {
        return sprintStatusRepository.findAll();
    }
    @Override
    public SprintStatus getSprintStatusByName(String name){
        return sprintStatusRepository.findSprintStatusByName(name);
    }
    @Override
    public Sprint completeSprint(Long idSprint){
        try{
            Sprint sprint= getSprint(idSprint);
            sprint.setSprintStatus(getSprintStatusByName("Completed"));
            return sprintRepository.save(sprint);
        }catch (NoSuchElementException e) {
            System.out.println("Sprint not found!");
            return new Sprint();
        }
    }
    @Override
    public Sprint startSprint(Long idSprint){
        try{
            Sprint sprint= getSprint(idSprint);
            sprint.setSprintStatus(getSprintStatusByName("In Progress"));
            return sprintRepository.save(sprint);
        }catch (NoSuchElementException e) {
            System.out.println("Sprint not found!");
            return new Sprint();
        }
    }
    @Override
    public Long getIdBySprintStatusInProgressAndProjectId(Long projectId){
        return sprintRepository.findIdBySprintStatusInProgressAndProjectId(projectId);
    }
    @Override
    public List<Sprint> getByProjectAndSprintStatus_Name(Project project, String sprintStatusName){
        return sprintRepository.findByProjectAndSprintStatus_Name(project,sprintStatusName);
    }
    @Override
    public void completeProjectSprints(Project project){
        System.out.println("Inside service:");
        List<Sprint> sprintsNotCompleted=sprintRepository.findByProjectAndSprintStatus_NameNotLike(project,"Completed");
        if(!sprintsNotCompleted.isEmpty()){
            for (int i=0; i<sprintsNotCompleted.size();i++){
                sprintsNotCompleted.get(i).setSprintStatus(sprintStatusRepository.findSprintStatusByName("Completed"));
                sprintRepository.save(sprintsNotCompleted.get(i));
            }
        }
    }
}
