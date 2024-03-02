package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.Sprint;
import com.example.ScrumWise.model.entity.SprintStatus;

import java.util.List;

public interface SprintService {

    Sprint getSprint(Long id);
    List<Sprint> getSprintsByProject(Long idProject);
    Sprint addSprint(Sprint sprint);
    Sprint updateSprint(Long id, Sprint sprint);
    void deleteSprint(Long id);
    Sprint completeSprint(Long idSprint);
    Sprint startSprint(Long idSprint);
    public void deleteSprintByProject(Project project);
    List<SprintStatus> getSprintStatuses();
    Sprint getLastSprintByProject(Project project);
    SprintStatus getSprintStatusByName(String name);
    Long getIdBySprintStatusInProgressAndProjectId(Long projectId);
    List<Sprint> getByProjectAndSprintStatus_Name(Project project, String sprintStatusName);
    void completeProjectSprints(Project project);
}
