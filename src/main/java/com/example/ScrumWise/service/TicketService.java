package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Sprint;
import com.example.ScrumWise.model.entity.Ticket;
import com.example.ScrumWise.model.entity.WorkflowItem;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketService {
    Ticket getTicketById(Long id);
    List<Ticket> getTicketListBySprint(Long idSprint);
    Ticket addTicket(Ticket ticket);
    Ticket updateTicket(Long id, Ticket ticket);
    Ticket updateTicketWorkflowItem(Long idTicket, Long idWorkflowItem);
    void deleteTicket(Long id);
    List<Ticket> getTicketsByWorkflowItem(WorkflowItem workflowItem);
    void updateTicketsWorkflowItem(Long oldIdWorkflowItem, Long newWorkflowItem);
    Ticket setEstimation(Long idTicket, Long estimation);

    List<Ticket> getTicketsBySprintAndWorkflowItemNotDone(Long idSprint);
    void moveTicketsNonCompletedToNextSprint(Long idOldSprint, Sprint newSprint);

}
