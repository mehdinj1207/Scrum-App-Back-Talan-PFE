package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.Feedback;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.User;
import com.example.ScrumWise.repository.FeedbackRepository;
import com.example.ScrumWise.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FeedbackServiceImp implements FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;
    @Override
    public Feedback getFeedbackById(Long id) {
        try{
            return feedbackRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Feedback not found!");
            return null;
        }
    }
    @Override
    public Feedback addFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }
    @Override
    public List<Feedback> getFeedbacksByProject(Project project) {
        return feedbackRepository.findByProject(project);
    }
    @Override
    public List<Feedback> getFeedbackByUser(User user){
        return feedbackRepository.findByUserOrderByDateFeedbackDesc(user);
    }
    @Override
    public List<Feedback> getFeedbacksByProjectAndUser(Project project, User user) {
        return feedbackRepository.findByProjectAndUser(project, user);
    }
    @Override
    public Feedback updateFeedback(Long id, Feedback feedback) {
        feedback.setIdFeedback(id);
        return feedbackRepository.save(feedback);
    }
    @Override
    public void deleteFeedbackById(Long id) {
        feedbackRepository.deleteById(id);
    }
    @Override
    public void deleteByProject(Project project) {
        feedbackRepository.deleteByProject(project);
    }
    @Override
    public void setIsUrgent(Long idFeedback, boolean isUrgent){
        Feedback feedback=feedbackRepository.findById(idFeedback).get();
        feedback.setUrgent(isUrgent);
        feedbackRepository.save(feedback);
    }
    @Override
    public Long countFeedbacksReplies(Feedback feedback){
        return feedbackRepository.countByReplyOnFeedback(feedback);
    }
}

