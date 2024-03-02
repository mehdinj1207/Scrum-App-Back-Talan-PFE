package com.example.ScrumWise.model.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "participation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Participation implements Serializable {
    /**
     * generated serial ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Participation id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idParticipation", updatable = false, nullable = false)
    private Long idParticipation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMeet")
    private Meet meet;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    private User user;


}
