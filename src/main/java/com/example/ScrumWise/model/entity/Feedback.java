package com.example.ScrumWise.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idFeedback",nullable = false)
    private Long idFeedback;
    @Column(name="content")
    private String content;
    @Column(name="star")
    private int star;
    @Column(name="isUrgent")
    private boolean isUrgent;
    @Column(name="dateFeedback")
    private LocalDateTime dateFeedback=LocalDateTime.now();
    @Column(name="dateEditFeedback")
    private LocalDateTime dateEditFeedback;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProject")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idReplyOnFeedback")
    private Feedback replyOnFeedback;

}

