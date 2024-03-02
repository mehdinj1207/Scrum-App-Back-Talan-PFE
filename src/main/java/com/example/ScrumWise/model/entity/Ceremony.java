package com.example.ScrumWise.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "ceremony")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ceremony implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCeremony",nullable = false)
    private Long idCeremony;

    @Column(name="name")
    private String title;
    @OneToMany(mappedBy = "ceremony", cascade=CascadeType.REMOVE)
    @JsonManagedReference
    private List<Meet> meets;
}
