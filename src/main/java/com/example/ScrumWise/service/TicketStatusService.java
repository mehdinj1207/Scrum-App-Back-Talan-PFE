package com.example.ScrumWise.service;
import com.example.ScrumWise.model.entity.TicketStatus;
import java.util.List;

public interface TicketStatusService {
    TicketStatus getTicketStatusById(Long id);
    List<TicketStatus> getTicketStatusList();
    TicketStatus getTicketStatusByName(String name);
    TicketStatus addTicketStatus(TicketStatus ticketStatus);
    TicketStatus updateTicketStatus(Long id, TicketStatus ticketStatus);
    void deleteTicketStatus(Long id);
}
