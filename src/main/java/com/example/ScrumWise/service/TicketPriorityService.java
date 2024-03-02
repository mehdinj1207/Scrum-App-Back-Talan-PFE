package com.example.ScrumWise.service;
import com.example.ScrumWise.model.entity.TicketPriority;
import java.util.List;

public interface TicketPriorityService {
    TicketPriority getTicketPriorityById(Long id);
    List<TicketPriority> getTicketPriorityList();
    TicketPriority addTicketPriority(TicketPriority ticketPriority);
    TicketPriority updateTicketPriority(Long id, TicketPriority ticketPriority);
    void deleteTicketPriority(Long id);
}
