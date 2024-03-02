package com.example.ScrumWise.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticket_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_ticket_type",nullable = false)
    private Long idTicketType;
    //{Story, Task, Bug}
    @Column(name="name")
    private String name;
}
