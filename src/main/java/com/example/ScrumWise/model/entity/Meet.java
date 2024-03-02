package com.example.ScrumWise.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "meet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Meet implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idMeet",nullable = false)
    private Long idMeet;

    @Column(name="title")
    private String title;
    @Column(name="repetition")
    private String repetition;
    @Column(name="description")
    private String description;
    @Column(name="duration")
    private String duration;
    @Column(name="allDay")
    private boolean allDay;
    @Column(name="roomName")
    private String roomName;

    @Column(name="startDate")
    private LocalDateTime startDate;
    @Column(name="endDate")
    private LocalDateTime endDate;

    @ManyToOne()
    @JoinColumn(name = "idProject")
    private Project project;

    @ManyToOne
    @JoinColumn(name="idCeremony")
    @JsonBackReference
    private Ceremony ceremony;
    @ManyToOne()
    @JoinColumn(name = "idUser")
    private User user;

}
