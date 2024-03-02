package com.example.ScrumWise.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ticket_assignment_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketAssignmentRole implements Serializable {
    /**
     * generated serial ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * RessourceRole id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTicketAssignmentRole", updatable = false, nullable = false)
    private Long idTicketAssignmentRole;

    @Column(name="role")
    private String role;

}
