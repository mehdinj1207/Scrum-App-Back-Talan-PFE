package com.example.ScrumWise.service;
import com.example.ScrumWise.model.entity.Feedback;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.User;

import java.util.List;

public interface FeedbackService {
    Feedback getFeedbackById(Long id);
    Feedback addFeedback(Feedback feedback);
    List<Feedback> getFeedbackByUser(User user);
    List<Feedback> getFeedbacksByProject(Project project);
    List<Feedback> getFeedbacksByProjectAndUser(Project project, User user);
    Feedback updateFeedback(Long id, Feedback feedback);
    void deleteFeedbackById(Long id);
    void deleteByProject(Project project);
    void setIsUrgent(Long idFeedback, boolean isUrgent);
    Long countFeedbacksReplies(Feedback feedback);
}

