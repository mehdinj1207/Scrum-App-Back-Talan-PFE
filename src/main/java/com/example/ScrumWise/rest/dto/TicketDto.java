package com.example.ScrumWise.rest.dto;
import com.example.ScrumWise.model.entity.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDto implements Serializable {
    private Long idTicket;
    private String name;
    private String note;
    private Long estimation;
    private Epic epic;
    private TicketStatusDto ticketStatus;
    private TicketTypeDto ticketType;
    private TicketPriorityDto ticketPriority;
    @JsonBackReference
    private WorkflowItemDto workflowItem;
}
