package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.TicketStatus;
import com.example.ScrumWise.repository.TicketStatusRepository;
import com.example.ScrumWise.service.TicketStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TicketStatusServiceImp implements TicketStatusService {
    @Autowired
    private TicketStatusRepository ticketStatusRepository ;
    @Override
    public TicketStatus getTicketStatusById(Long id) {
        try{
            return ticketStatusRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Ticket Status not found!");
            return new TicketStatus();
        }
    }
    @Override
    public TicketStatus getTicketStatusByName(String name){
        return ticketStatusRepository.findByName(name);
    }

    @Override
    public List<TicketStatus> getTicketStatusList() {
        return ticketStatusRepository.findAll();
    }

    @Override
    public TicketStatus addTicketStatus(TicketStatus ticketStatus) {
        return ticketStatusRepository.save(ticketStatus);
    }

    @Override
    public TicketStatus updateTicketStatus(Long id, TicketStatus ticketStatus) {
        getTicketStatusById(id);
        ticketStatus.setIdTicketStatus(id);
        return ticketStatusRepository.save(ticketStatus);
    }

    @Override
    public void deleteTicketStatus(Long id) {
        ticketStatusRepository.deleteById(id);
    }
}
