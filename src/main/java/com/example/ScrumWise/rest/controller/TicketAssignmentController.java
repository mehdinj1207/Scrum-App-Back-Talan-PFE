package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.exception.ResourceNotFoundException;
import com.example.ScrumWise.model.entity.*;
import com.example.ScrumWise.rest.dto.RessourceDto;
import com.example.ScrumWise.rest.dto.TicketAssignmentDto;
import com.example.ScrumWise.rest.dto.UserDto;
import com.example.ScrumWise.service.TicketAssignmentRoleService;
import com.example.ScrumWise.service.TicketAssignmentService;
import com.example.ScrumWise.service.TicketService;
import com.example.ScrumWise.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.Type;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@Controller
public class TicketAssignmentController {
    @Autowired
    private TicketAssignmentService ticketAssignmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketAssignmentRoleService ticketAssignmentRoleService;
    @Autowired
    private ModelMapper modelMapper ;

    @GetMapping("/ticketAssignment")
    @RolesAllowed({"Manager","Consultant"})
    public Object TicketAssignmentList() {
        List<TicketAssignment> ticketAssignments = ticketAssignmentService.getTicketAssignmentList();
        Type listType = new TypeToken<List<TicketAssignmentDto>>() {}.getType() ;
        List<TicketAssignmentDto> ticketAssignmentDtos = modelMapper.map(ticketAssignments,listType);
        return ResponseEntity.status(HttpStatus.OK).body(ticketAssignmentDtos);
    }
    @GetMapping("/ticketAssignment/{idTicketAssignment}")
    @RolesAllowed({"Manager","Consultant"})
    public Object TicketAssignment(@PathVariable Long idTicketAssignment ) {
        TicketAssignment ticketAssignment = ticketAssignmentService.getTicketAssignmentById(idTicketAssignment) ;
        if (ticketAssignment.getIdTicketAssignment()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            TicketAssignmentDto ticketAssignmentDto = modelMapper.map(ticketAssignment, TicketAssignmentDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(ticketAssignmentDto);
        }
    }
    @GetMapping("/ticketAssignment/Users/{idTicket}")
    @RolesAllowed({"Manager","Consultant"})
    public Object TicketAssignmentByTicket(@PathVariable Long idTicket ) {

        List<User> list = ticketAssignmentService.getTicketAssignmentByTicket(ticketService.getTicketById(idTicket)) ;
        if (list == null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
           Type listType = new TypeToken<List<UserDto>>() {}.getType() ;
            List<UserDto> userDtos = modelMapper.map(list,listType);
            return ResponseEntity.status(HttpStatus.OK).body(userDtos);

        }
    }
    @GetMapping("/ticketAssignment/ticket/{idTicket}/user/{idUser}")
    @RolesAllowed({"Manager","Consultant"})
    public Object GetTicketAssignmentByProjectAndUser(@PathVariable Long idTicket, @PathVariable Long idUser) {
        try {
            TicketAssignment ticketAssignment = ticketAssignmentService.getTicketAssignmentByUserAndTicket(
                    userService.getUserById(idUser),
                    ticketService.getTicketById(idTicket)
            );
            TicketAssignmentDto ticketAssignmentDto = modelMapper.map(ticketAssignment, TicketAssignmentDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(ticketAssignmentDto);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PostMapping("/ticketAssignment/{idTicket}/{idUser}/{idRole}")
    @RolesAllowed("Manager")
    public Object AddTicketAssignment(@PathVariable Long idTicket,
                               @PathVariable Long idUser,@PathVariable Long idRole)
    {
        TicketAssignment ticketAssignment = new TicketAssignment();
        ticketAssignment.setTicketAssignmentRole(ticketAssignmentRoleService.getTicketAssignmentRoleById(idRole));
        ticketAssignment.setUser(userService.getUserById(idUser));
        ticketAssignment.setTicket(ticketService.getTicketById(idTicket));
        ticketAssignment = ticketAssignmentService.addTicketAssignment(ticketAssignment);
        TicketAssignmentDto ticketAssignmentDto = modelMapper.map(ticketAssignment, TicketAssignmentDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketAssignmentDto);
    }

    @PutMapping("/ticketAssignment/{idTicket}/{idUser}/{idRole}")
    @RolesAllowed("Manager")
    public Object UpdateTicketAssignment (@PathVariable Long idTicket, @PathVariable Long idUser,
                                   @PathVariable Long idRole) {
        User user= userService.getUserById(idUser);
        Ticket ticket = ticketService.getTicketById(idTicket);
        TicketAssignment ticketAssignment = ticketAssignmentService.getTicketAssignmentByUserAndTicket(user, ticket);
        ticketAssignment.setTicketAssignmentRole(ticketAssignmentRoleService.getTicketAssignmentRoleById(idRole));
        ticketAssignment = ticketAssignmentService.updateTicketAssignment(ticketAssignment.getIdTicketAssignment(), ticketAssignment);
        TicketAssignmentDto ticketAssignmentDto = modelMapper.map(ticketAssignment, TicketAssignmentDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketAssignmentDto);
    }
    @DeleteMapping("/ticketAssignment/{idTicketAssignment}")
    @RolesAllowed("Manager")
    public Object DeleteTicketAssignment(@PathVariable Long idTicketAssignment) {
        ticketAssignmentService.deleteTicketAssignment(idTicketAssignment);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
