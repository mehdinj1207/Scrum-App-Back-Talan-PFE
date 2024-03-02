package com.example.ScrumWise.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="feedback_receivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackReceivers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idFeedbackReceivers",nullable = false)
    private Long idFeedbackReceivers;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFeedback")
    private Feedback feedback;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    private User user;
    @Column(name="isRead")
    private boolean isRead=false;
    @Column(name="isImportant")
    private boolean isImportant=false;
}
