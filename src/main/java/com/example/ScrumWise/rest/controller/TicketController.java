package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.Epic;
import com.example.ScrumWise.model.entity.Ticket;
import com.example.ScrumWise.rest.dto.EpicDto;
import com.example.ScrumWise.rest.dto.TicketDto;
import com.example.ScrumWise.rest.dto.TicketDto2;
import com.example.ScrumWise.service.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.Type;
import java.util.List;
@RestController
@CrossOrigin
public class TicketController {
    @Autowired
    private SprintService sprintService;
    @Autowired
    private TicketService ticketService ;
    @Autowired
    private TicketStatusService ticketStatusService;
    @Autowired
    private TicketTypeService ticketTypeService;
    @Autowired
    private TicketPriorityService ticketPriorityService;
    @Autowired
    private EpicService epicService;
    @Autowired
    private TicketAssignmentService ticketAssignmentService;
    @Autowired
    private WorkflowItemService workflowItemService;

    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/tickets/sprint/{idSprint}")
    @RolesAllowed({"Manager","Consultant"})
    public Object TicketList(@PathVariable Long idSprint) {
        List<Ticket> tickets = ticketService.getTicketListBySprint(idSprint);
        Type listType = new TypeToken<List<TicketDto2>>() {}.getType() ;
        List<TicketDto2> ticketDtos = modelMapper.map(tickets,listType);
        return ResponseEntity.status(HttpStatus.OK).body(ticketDtos);
    }
    @GetMapping("/tickets/{idTicket}")
    @RolesAllowed({"Manager","Consultant"})
    public Object ticket(@PathVariable Long idTicket ) {
        Ticket ticket = ticketService.getTicketById(idTicket) ;
        if (ticket.getIdTicket()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            TicketDto2 ticketDto = modelMapper.map(ticket, TicketDto2.class);
            return ResponseEntity.status(HttpStatus.OK).body(ticketDto);
        }
    }
    @GetMapping("/tickets/sprint/not-done/{idSprint}")
    @RolesAllowed({"Manager","Consultant"})
    public Object ticketsBySprintAndWorkflowItemNotDone(@PathVariable Long idSprint ) {
        try{
            List<Ticket> tickets =  ticketService.getTicketsBySprintAndWorkflowItemNotDone(idSprint) ;
            Type listType = new TypeToken<List<TicketDto>>() {}.getType() ;
            List<TicketDto> ticketDtos = modelMapper.map(tickets,listType);
            System.out.println("length: "+tickets.size());
            return ResponseEntity.status(HttpStatus.OK).body(ticketDtos);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \""+e.getMessage()+"\"}");
        }
    }

    @PostMapping("/tickets/sprint/{idSprint}/type/{idType}/priority/{idPriority}/epic/{idEpic}")
    @RolesAllowed("Manager")
    public Object addTicket(@Validated @RequestBody TicketDto ticketDto,
                            @PathVariable Long idSprint, @PathVariable Long idType,
                            @PathVariable Long idPriority, @PathVariable Long idEpic)
    {
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
        ticket.setEpic(epicService.getEpicById(idEpic));
        ticket.setTicketStatus(ticketStatusService.getTicketStatusByName("To Do"));
        ticket.setWorkflowItem(workflowItemService.getWorkflowItemByNameAndSprint("To Do",sprintService.getSprint(idSprint)));
        ticket.setTicketType(ticketTypeService.getTicketTypeById(idType));
        ticket.setTicketPriority(ticketPriorityService.getTicketPriorityById(idPriority));
        ticket = ticketService.addTicket(ticket);
        ticketDto = modelMapper.map(ticket, TicketDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketDto);
    }

    @PutMapping("/tickets/{idTicket}/type/{idType}/priority/{idPriority}/status/{idStatus}/epic/{idEpic}")
    @RolesAllowed("Manager")
    public Object updateTicket (@Validated @RequestBody TicketDto ticketDto,
                                @PathVariable Long idTicket, @PathVariable Long idType,
                                @PathVariable Long idPriority, @PathVariable Long idStatus,
                                @PathVariable Long idEpic) {
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
        ticket.setTicketType(ticketTypeService.getTicketTypeById(idType));
        ticket.setTicketPriority(ticketPriorityService.getTicketPriorityById(idPriority));
        ticket.setTicketStatus(ticketStatusService.getTicketStatusById(idStatus));
        ticket.setEpic(epicService.getEpicById(idEpic));
        ticket = ticketService.updateTicket(idTicket, ticket);
        ticketDto = modelMapper.map(ticket, TicketDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(ticketDto);
    }
    @GetMapping("/tickets/{idTicket}/workflowItem/{idWorkflowItem}")
    @RolesAllowed({"Manager","Consultant"})
    public Object updateTicketWorkflowItem (@PathVariable Long idTicket, @PathVariable Long idWorkflowItem) {
        Ticket ticket = ticketService.updateTicketWorkflowItem(idTicket,idWorkflowItem);
        TicketDto2 ticketDto = modelMapper.map(ticket, TicketDto2.class);
        return ResponseEntity.status(HttpStatus.OK).body(ticketDto);
    }
    @DeleteMapping("/tickets/{idTicket}")
    @RolesAllowed("Manager")
    public Object deleteTicket(@PathVariable Long idTicket) {
        ticketAssignmentService.deleteTicketAssignmentByTicket(ticketService.getTicketById(idTicket));
        ticketService.deleteTicket(idTicket);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
    @GetMapping("/tickets/{idTicket}/estimation/{estimation}")
    @RolesAllowed("Manager")
    public Object setTicketEstimation(@PathVariable Long idTicket, @PathVariable Long estimation) {
        Ticket ticket= ticketService.setEstimation(idTicket, estimation);
        TicketDto2 ticketDto = modelMapper.map(ticket, TicketDto2.class);
        return ResponseEntity.status(HttpStatus.OK).body(ticketDto);
    }
}
