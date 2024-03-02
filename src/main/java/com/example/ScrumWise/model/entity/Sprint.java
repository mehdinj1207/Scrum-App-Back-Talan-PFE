package com.example.ScrumWise.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "sprint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sprint implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_sprint",nullable = false)
    private Long idSprint;

    @Column(name="name")
    private String name;
    @Column(name="objective")
    private String objective;

    @Column(name="total_estimation")
    private Long totalEstimation;

    @Column(name="sprint_number")
    private Long sprintNumber;

    //Available Statuses are : { 'Created','In Progress','Completed' }
    @ManyToOne
    @JoinColumn(name="id_sprint_status")
    private SprintStatus sprintStatus;

    @Column(name="start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name="end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name="id_project")
    @JsonBackReference
    private Project project;

    @OneToMany(mappedBy="sprint", cascade=CascadeType.REMOVE)
    @JsonManagedReference
    private List<WorkflowItem> workflowItems;
}
