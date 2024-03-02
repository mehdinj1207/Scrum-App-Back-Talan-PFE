package com.example.ScrumWise.service.serviceImp;


import com.example.ScrumWise.model.entity.Feedback;
import com.example.ScrumWise.model.entity.FeedbackReceivers;
import com.example.ScrumWise.model.entity.User;
import com.example.ScrumWise.repository.FeedbackReceiversRepository;
import com.example.ScrumWise.service.FeedbackReceiversService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackReceiversServiceImp implements FeedbackReceiversService {
    @Autowired
    FeedbackReceiversRepository feedbackReceiversRepository;
    @Override
    public List<User> getReceiversByFeedback(Feedback feedback) {
        return feedbackReceiversRepository.findUsersByFeedback(feedback);
    }

    @Override
    public List<Feedback> getFeedbackReceivedByUser(User user) {
        return feedbackReceiversRepository.findFeedbacksByUser(user);
    }

    @Override
    public void addReceivers(Feedback feedback, List<User> users) {
        for(int i=0; i<users.size(); i++){
            addReceiver(feedback, users.get(i));
        }
    }

    @Override
    public void addReceiver(Feedback feedback, User user) {
        FeedbackReceivers feedbackReceivers=new FeedbackReceivers();
        feedbackReceivers.setFeedback(feedback);
        feedbackReceivers.setUser(user);
        feedbackReceiversRepository.save(feedbackReceivers);
    }

    @Override
    public void deleteFeedbackReceiversByFeedback(Feedback feedback) {
        feedbackReceiversRepository.deleteByFeedback(feedback);
    }

    @Override
    public void deleteByUserAndFeedback(User user, Feedback feedback) {
        feedbackReceiversRepository.deleteByFeedbackAndUser(feedback, user);
    }
    @Override
    public void deleteById(Long id) {
        feedbackReceiversRepository.deleteById(id);
    }
    @Override
    public void setIsImportant(User user, Feedback feedback, boolean isImportant) {
        FeedbackReceivers feedbackReceiver= feedbackReceiversRepository.findByUserAndFeedback(user, feedback);
        feedbackReceiver.setImportant(isImportant);
        feedbackReceiversRepository.save(feedbackReceiver);
    }
    @Override
    public void setIsRead(User user, Feedback feedback, boolean isRead) {
        FeedbackReceivers feedbackReceiver= feedbackReceiversRepository.findByUserAndFeedback(user, feedback);
        feedbackReceiver.setRead(isRead);
        feedbackReceiversRepository.save(feedbackReceiver);
    }
    @Override
    public List<FeedbackReceivers> getFeedbackReceiversByUser(User user){
        return feedbackReceiversRepository.findByUser(user);
    }
}
