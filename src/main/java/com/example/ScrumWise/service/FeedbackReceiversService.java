package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Feedback;
import com.example.ScrumWise.model.entity.FeedbackReceivers;
import com.example.ScrumWise.model.entity.User;

import java.util.List;

public interface FeedbackReceiversService {
    List<User> getReceiversByFeedback(Feedback feedback);
    List<Feedback> getFeedbackReceivedByUser(User user);
    void addReceivers(Feedback feedback, List<User> users);
    void addReceiver(Feedback feedback, User user);
    void deleteFeedbackReceiversByFeedback(Feedback feedback);
    void deleteByUserAndFeedback(User user, Feedback feedback);
    void deleteById(Long id);
    void setIsImportant(User user, Feedback feedback, boolean isImportant);
    void setIsRead(User user, Feedback feedback, boolean isRead);
    List<FeedbackReceivers> getFeedbackReceiversByUser(User user);
}
