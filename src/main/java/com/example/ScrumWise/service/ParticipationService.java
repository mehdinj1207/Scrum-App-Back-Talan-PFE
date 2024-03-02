package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Meet;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.User;

import java.util.List;

public interface ParticipationService {
    void addParticipation(Meet meet, User user);
    void deleteParticipation(Meet meet, User user);
    List<User> getParticipants(Meet meet);
    List<Meet> getMeetingByParticipant(User user);
    void deleteParticipationByMeet(Meet meet);
    void deleteById(Long id);
    List<Project> getDistinctProjectsByUserId(User user);
    void deleteParticipationsByProjectId(Long idProject);
}

