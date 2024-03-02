package com.example.ScrumWise.service;
import com.example.ScrumWise.model.entity.TicketType;
import java.util.List;

public interface TicketTypeService {
    TicketType getTicketTypeById(Long id);
    List<TicketType> getTicketTypeList();
    TicketType addTicketType(TicketType ticketType);
    TicketType updateTicketType(Long id, TicketType ticketType);
    void deleteTicketType(Long id);
}
