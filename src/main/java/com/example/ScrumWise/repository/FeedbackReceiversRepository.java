package com.example.ScrumWise.repository;
import com.example.ScrumWise.model.entity.Feedback;
import com.example.ScrumWise.model.entity.FeedbackReceivers;
import com.example.ScrumWise.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackReceiversRepository  extends JpaRepository<FeedbackReceivers,Long> {
    @Query("SELECT fr.user FROM FeedbackReceivers fr WHERE fr.feedback = :feedback")
    List<User> findUsersByFeedback(@Param("feedback") Feedback feedback);
    void deleteByFeedback(Feedback feedback);
    void deleteByFeedbackAndUser(Feedback feedback, User user);
    @Query("SELECT fr.feedback FROM FeedbackReceivers fr WHERE fr.user = :user")
    List<Feedback> findFeedbacksByUser(@Param("user") User user);
    FeedbackReceivers findByUserAndFeedback(User user, Feedback feedback);
    List<FeedbackReceivers> findByUser(User user);

}

