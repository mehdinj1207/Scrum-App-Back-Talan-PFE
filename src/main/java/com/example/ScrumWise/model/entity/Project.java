package com.example.ScrumWise.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "Project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project implements Serializable {
    /**
     * generated serial ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Project id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idProject", updatable = false, nullable = false)
    private Long idProject;

    @Column(name="reference")
    private String reference;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="owner")
    private String owner;


    @ManyToOne
    @JoinColumn(name="id_project_status")
    private ProjectStatus projectStatus;

    @Column(name="dateCreation")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation;

    @Column(name="endDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "idDepartment")
    private Department department;


    @OneToMany(mappedBy = "project", cascade=CascadeType.REMOVE)
    @JsonManagedReference
    private List<Sprint> sprints;

    @OneToMany(mappedBy = "project", cascade=CascadeType.REMOVE)
    @JsonManagedReference
    private List<Attachment> attachments;

}
