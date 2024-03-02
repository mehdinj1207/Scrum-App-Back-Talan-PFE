package com.example.ScrumWise.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department implements Serializable {
    /**
     * generated serial ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Department id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idDepartment", updatable = false, nullable = false)
    private Long idDepartment;

    @Column(name="name")
    private String name;

    @Column(name="color")
    private String color;
}
