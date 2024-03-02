package com.example.ScrumWise.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticket_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_ticket_status",nullable = false)
    private Long idTicketStatus;
    //{ To Do, In Progress, Done}
    @Column(name="name")
    private String name;
}
