package com.example.ScrumWise.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "workflowItem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkflowItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_workflowItem",nullable = false)
    private Long idWorkflowItem;

    @Column(name="name")
    private String name;

    @Column(name="order_number")
    private int orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSprint")
    @JsonBackReference
    private Sprint sprint;

    @OneToMany(mappedBy="workflowItem", cascade=CascadeType.REMOVE)
    private List<Ticket> tickets;

    public WorkflowItem(String name, int orderNumber){
        this.name=name;
        this.orderNumber=orderNumber;
    }
}
