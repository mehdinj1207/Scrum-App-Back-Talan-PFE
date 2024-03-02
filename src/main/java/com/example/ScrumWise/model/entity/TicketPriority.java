package com.example.ScrumWise.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "ticket_priority")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketPriority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_ticket_priority",nullable = false)
    private Long idTicketPriority;
    //{High, Low, Medium}
    @Column(name="name")
    private String name;
}
