package com.example.ScrumWise.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ressource")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ressource implements Serializable{
    /**
     * generated serial ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * RessourceRole id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idRessource", updatable = false, nullable = false)
    private Long idRessource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProject")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRole")
    private RessourceRole role;


}
