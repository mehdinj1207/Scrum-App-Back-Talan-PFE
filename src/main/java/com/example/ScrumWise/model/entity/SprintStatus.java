package com.example.ScrumWise.model.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sprint_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SprintStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_sprint_status",nullable = false)
    private Long idSprintStatus;

    @Column(name="name")
    private String name;
}
