package com.example.ScrumWise.repository;
import com.example.ScrumWise.model.entity.Feedback;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    List<Feedback> findByProject(Project project);
    List<Feedback> findByUserOrderByDateFeedbackDesc(User user);
    List<Feedback> findByProjectAndUser(Project project, User user);
    void deleteByProject(Project project);
    Long countByReplyOnFeedback(Feedback feedback);
}