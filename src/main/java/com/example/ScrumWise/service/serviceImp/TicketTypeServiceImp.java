package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.TicketType;
import com.example.ScrumWise.repository.TicketTypeRepository;
import com.example.ScrumWise.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TicketTypeServiceImp implements TicketTypeService {
    @Autowired
    private TicketTypeRepository ticketTypeRepository ;
    @Override
    public TicketType getTicketTypeById(Long id) {
        try{
            return ticketTypeRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Ticket not found!");
            return new TicketType();
        }
    }

    @Override
    public List<TicketType> getTicketTypeList() {
        return ticketTypeRepository.findAll();
    }

    @Override
    public TicketType addTicketType(TicketType ticketType) {
        return ticketTypeRepository.save(ticketType);
    }

    @Override
    public TicketType updateTicketType(Long id, TicketType ticketType) {
        getTicketTypeById(id);
        ticketType.setIdTicketType(id);
        return ticketTypeRepository.save(ticketType);
    }

    @Override
    public void deleteTicketType(Long id) {
        ticketTypeRepository.deleteById(id);
    }
}
