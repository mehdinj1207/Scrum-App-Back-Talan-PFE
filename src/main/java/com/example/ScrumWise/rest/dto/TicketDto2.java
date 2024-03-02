package com.example.ScrumWise.rest.dto;

import com.example.ScrumWise.model.entity.Epic;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDto2 {
    private Long idTicket;
    private String name;
    private String note;
    private Long estimation;
    private Epic epic;
    private TicketStatusDto ticketStatus;
    private TicketTypeDto ticketType;
    private TicketPriorityDto ticketPriority;
    private WorkflowItemDto2 workflowItem;
}