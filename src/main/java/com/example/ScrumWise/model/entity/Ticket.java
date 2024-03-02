package com.example.ScrumWise.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_ticket",nullable = false)
    private Long idTicket;

    @Column(name="name")
    private String name;

    @Column(name="note")
    private String note;

    @Column(name="estimation")
    private Long estimation;

    //Available Statuses are : { 'Created','In Progress','Completed' }
    @ManyToOne
    @JoinColumn(name="id_ticket_status")
    private TicketStatus ticketStatus;

    //Available Types are : { 'Story','Bug','Task' }
    @ManyToOne
    @JoinColumn(name="id_ticket_type")
    private TicketType ticketType;

    //Available Priorities are : { 'Low','Medium','High' }
    @ManyToOne
    @JoinColumn(name="id_ticket_priority")
    private TicketPriority ticketPriority;
    @ManyToOne
    @JoinColumn(name="id_epic")
    private Epic epic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_workflowItem")
    private WorkflowItem workflowItem;

    @OneToMany(mappedBy="ticket", cascade=CascadeType.REMOVE)
    @JsonManagedReference
    private List<TicketAssignment> workflowItems;

}
