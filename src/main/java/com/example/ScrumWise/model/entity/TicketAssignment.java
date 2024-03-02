package com.example.ScrumWise.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ticket_assignment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketAssignment implements Serializable {
    /**
     * generated serial ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * TicketAssignment id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTicketAssignment", updatable = false, nullable = false)
    private Long idTicketAssignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTicket")
    @JsonBackReference
    private Ticket ticket;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTicketAssignmentRole")
    private TicketAssignmentRole ticketAssignmentRole;



}

