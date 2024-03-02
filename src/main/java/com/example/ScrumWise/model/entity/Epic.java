package com.example.ScrumWise.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "epic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Epic implements Serializable {
    /**
     * generated serial ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Country id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idEpic", updatable = false, nullable = false)
    private Long idEpic;

    @Column(name="name")
    private String name;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="id_project")
    private Project project;



}
