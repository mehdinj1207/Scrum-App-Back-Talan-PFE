package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.*;

import java.util.List;

public interface TicketAssignmentService {
    TicketAssignment getTicketAssignmentById(Long id);
    List<TicketAssignment> getTicketAssignmentList();
    List<User> getTicketAssignmentByTicket(Ticket ticket);
    TicketAssignment addTicketAssignment(TicketAssignment ticketAssignment);
    TicketAssignment updateTicketAssignment(Long id, TicketAssignment ticketAssignment);
    void deleteTicketAssignment(Long id);
    TicketAssignment getTicketAssignmentByUserAndTicket(User user, Ticket ticket);
    void deleteTicketAssignmentByTicket(Ticket ticket);
    void deleteTicketAssignmentByUser(User user);
}
