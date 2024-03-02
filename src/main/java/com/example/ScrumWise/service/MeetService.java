package com.example.ScrumWise.service;

import com.example.ScrumWise.message.EmailDetails;
import com.example.ScrumWise.model.entity.Meet;
import com.example.ScrumWise.model.entity.Project;

import java.util.List;

public interface MeetService {
    Meet getMeetById(Long id);
    List<Meet> getMeetList();
    Meet addMeet(Meet meet);
    Meet updateMeet(Long id, Meet meet);
    void deleteMeet(Long id);
    List<Project> getDistinctProjectsByUserId(Long userId);
    String generateRoomName();
    String sendMeetInvitation(EmailDetails details);
    void deleteMeetsByProject(Project project);
}
