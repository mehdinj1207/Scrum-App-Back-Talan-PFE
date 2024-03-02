package com.example.ScrumWise.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="project_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_project_status",nullable = false)
    private Long idProjectStatus;


    /*
    @OneToMany(mappedBy="projectStatus")
    private Set<Project> projects  = new HashSet<Project>() ;

     */

    @Column(name="status_name" )
    private String statusName;




}
