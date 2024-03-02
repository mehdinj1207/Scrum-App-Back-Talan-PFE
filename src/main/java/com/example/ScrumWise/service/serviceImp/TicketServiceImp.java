package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.Sprint;
import com.example.ScrumWise.model.entity.Ticket;
import com.example.ScrumWise.model.entity.WorkflowItem;
import com.example.ScrumWise.repository.TicketRepository;
import com.example.ScrumWise.repository.WorkflowItemRepository;
import com.example.ScrumWise.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TicketServiceImp implements TicketService{

    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    WorkflowItemRepository workflowItemRepository;
    @Override
    public Ticket getTicketById(Long id) {
        try{
            return ticketRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Ticket not found!");
            return new Ticket();
        }
    }

    @Override
    public List<Ticket> getTicketListBySprint(Long idSprint) {

        return ticketRepository.findBySprintId(idSprint);
    }

    @Override
    public Ticket addTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket updateTicket(Long id, Ticket ticket) {
        getTicketById(id);
        ticket.setIdTicket(id);
        return ticketRepository.save(ticket);
    }
    @Override
    public Ticket updateTicketWorkflowItem(Long idTicket, Long idWorkflowItem){
        Ticket ticket=getTicketById(idTicket);
        ticket.setWorkflowItem(workflowItemRepository.findById(idWorkflowItem).get());
        return ticketRepository.save(ticket);
    }
    @Override
    public List<Ticket> getTicketsByWorkflowItem(WorkflowItem workflowItem) {
        return ticketRepository.findByWorkflowItem(workflowItem);
    }

    @Override
    public void deleteTicket(Long id) {

        ticketRepository.deleteById(id);
    }
    @Override
    public void updateTicketsWorkflowItem(Long oldIdWorkflowItem, Long newWorkflowItem){
        ticketRepository.updateTicketWorkflowItemId(oldIdWorkflowItem,newWorkflowItem);
    }
    @Override
    public Ticket setEstimation(Long idTicket, Long estimation) {
        Ticket ticket= getTicketById(idTicket);
        ticket.setEstimation(estimation);
        return ticketRepository.save(ticket);
    }
    @Override
    public List<Ticket> getTicketsBySprintAndWorkflowItemNotDone(Long idSprint){
        return ticketRepository.findByWorkflowItem_Sprint_IdSprintAndWorkflowItem_NameNot(idSprint, "Done");
    }
    @Override
    public void moveTicketsNonCompletedToNextSprint(Long idOldSprint, Sprint newSprint){
        List<Ticket> ticketsOldSprint=ticketRepository.findByWorkflowItem_Sprint_IdSprintAndWorkflowItem_NameNot(idOldSprint, "Done");
        WorkflowItem workflowItem=workflowItemRepository.findByNameAndSprint("To Do", newSprint);
        if (!ticketsOldSprint.isEmpty()){
            for (int i=0; i<ticketsOldSprint.size();i++){
                ticketsOldSprint.get(i).setWorkflowItem(workflowItem);
                ticketRepository.save(ticketsOldSprint.get(i));
            }
        }
    }
}
