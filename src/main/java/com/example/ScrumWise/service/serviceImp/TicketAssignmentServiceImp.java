package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.exception.ResourceNotFoundException;
import com.example.ScrumWise.model.entity.*;
import com.example.ScrumWise.repository.TicketAssignmentRepository;
import com.example.ScrumWise.service.TicketAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TicketAssignmentServiceImp implements TicketAssignmentService {
    @Autowired
    private TicketAssignmentRepository ticketAssignmentRepository ;
    @Override
    public TicketAssignment getTicketAssignmentById(Long id) {
        try{
            return ticketAssignmentRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Assignment not found!");
            return new TicketAssignment();
        }
    }
    @Override
    public List<User> getTicketAssignmentByTicket(Ticket ticket) {
        List<TicketAssignment> list = ticketAssignmentRepository.findTicketAssignmentByTicket(ticket);
        List<User> listUser = new ArrayList<User>();
        for (int i = 0; i < list.size(); i++) {
            listUser.add(list.get(i).getUser());
        }
        return listUser;
    }
    @Override
    public TicketAssignment getTicketAssignmentByUserAndTicket(User user, Ticket ticket) {
        TicketAssignment ticketAssignment = ticketAssignmentRepository.findTicketAssignmentByTicketAndUser(user, ticket);
        if (ticketAssignment == null) {
            throw new ResourceNotFoundException("No Assignment found " );
        }
        return ticketAssignment;
    }
    @Override
    public List<TicketAssignment> getTicketAssignmentList() {
        return ticketAssignmentRepository.findAll();
    }

    @Override
    public TicketAssignment addTicketAssignment(TicketAssignment ticketAssignment) {
        return ticketAssignmentRepository.save(ticketAssignment);
    }

    @Override
    public TicketAssignment updateTicketAssignment(Long id, TicketAssignment ticketAssignment) {
        getTicketAssignmentById(id);
        ticketAssignment.setIdTicketAssignment(id);
        return ticketAssignmentRepository.save(ticketAssignment);
    }

    @Override
    public void deleteTicketAssignment(Long id) {
        ticketAssignmentRepository.deleteById(id);
    }

    @Override
    public void deleteTicketAssignmentByUser(User user){
        List <TicketAssignment> ticketAssignment = ticketAssignmentRepository.findTicketAssignmentByUser(user);
        for (int i = 0 ;i<ticketAssignment.size();i++){
            ticketAssignmentRepository.delete(ticketAssignment.get(i));
        }
    }
    @Override
    public void deleteTicketAssignmentByTicket(Ticket ticket){
        List <TicketAssignment> ticketAssignment = ticketAssignmentRepository.findTicketAssignmentByTicket(ticket);
        for (int i = 0 ;i<ticketAssignment.size();i++){
            ticketAssignmentRepository.delete(ticketAssignment.get(i));
        }
    }
}
