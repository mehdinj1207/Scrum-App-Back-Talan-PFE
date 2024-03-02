package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.TicketPriority;
import com.example.ScrumWise.repository.TicketPriorityRepository;
import com.example.ScrumWise.service.TicketPriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
@Service
public class TicketPriorityServiceImp implements TicketPriorityService {
    @Autowired
    private TicketPriorityRepository ticketPriorityRepository ;
    @Override
    public TicketPriority getTicketPriorityById(Long id) {
        try{
            return ticketPriorityRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Ticket priority not found!");
            return new TicketPriority();
        }
    }

    @Override
    public List<TicketPriority> getTicketPriorityList() {
        return ticketPriorityRepository.findAll();
    }

    @Override
    public TicketPriority addTicketPriority(TicketPriority ticketPriority) {
        return ticketPriorityRepository.save(ticketPriority);
    }

    @Override
    public TicketPriority updateTicketPriority(Long id, TicketPriority ticketPriority) {
        getTicketPriorityById(id);
        ticketPriority.setIdTicketPriority(id);
        return ticketPriorityRepository.save(ticketPriority);
    }

    @Override
    public void deleteTicketPriority(Long id) {
        ticketPriorityRepository.deleteById(id);
    }
}
