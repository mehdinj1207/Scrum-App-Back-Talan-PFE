package com.example.ScrumWise.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "country")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Country implements Serializable {
    /**
     * generated serial ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Country id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCountry", updatable = false, nullable = false)
    private Long idCountry;

    @Column(name="name")
    private String name;

    @Column(name="iso")
    private String iso;
    @Column(name="code")
    private String code;

    @Column(name="flagImagePos")
    private String flagImagePos;
}
