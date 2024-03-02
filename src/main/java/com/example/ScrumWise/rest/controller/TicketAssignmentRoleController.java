package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.TicketAssignmentRole;
import com.example.ScrumWise.rest.dto.TicketAssignmentRoleDto;

import com.example.ScrumWise.service.TicketAssignmentRoleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.Type;
import java.util.List;
@CrossOrigin(maxAge = 3600)
@Controller
public class TicketAssignmentRoleController {
    @Autowired
    private TicketAssignmentRoleService ticketAssignmentRoleService ;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/ticketAssignmentRole")
    @RolesAllowed({"Manager","Consultant"})
    public Object TicketAssignmentRoleList() {
        List<TicketAssignmentRole> TicketAssignmentRoles = ticketAssignmentRoleService.getTicketAssignmentRoleList();
        Type listType = new TypeToken<List<TicketAssignmentRoleDto>>() {}.getType() ;
        List<TicketAssignmentRoleDto> ticketAssignmentRoleDtos = modelMapper.map(TicketAssignmentRoles,listType);
        return ResponseEntity.status(HttpStatus.OK).body(ticketAssignmentRoleDtos);
    }
    @GetMapping("/ticketAssignmentRole/{idTicketAssignmentRole}")
    @RolesAllowed({"Manager","Consultant"})
    public Object TicketAssignmentRole(@PathVariable Long idTicketAssignmentRoleRole ) {
        TicketAssignmentRole ticketAssignmentRole = ticketAssignmentRoleService.getTicketAssignmentRoleById(idTicketAssignmentRoleRole) ;
        if (ticketAssignmentRole.getIdTicketAssignmentRole()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            TicketAssignmentRoleDto ticketAssignmentRoleDto = modelMapper.map(ticketAssignmentRole, TicketAssignmentRoleDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(ticketAssignmentRoleDto);
        }
    }

    @PostMapping("/ticketAssignmentRole")
    @RolesAllowed("Manager")
    public Object AddTicketAssignmentRole(@Validated @RequestBody TicketAssignmentRoleDto ticketAssignmentRoleDto)
    {
        TicketAssignmentRole ticketAssignmentRole = modelMapper.map(ticketAssignmentRoleDto, TicketAssignmentRole.class);
        ticketAssignmentRole = ticketAssignmentRoleService.addTicketAssignmentRole(ticketAssignmentRole);
        ticketAssignmentRoleDto = modelMapper.map(ticketAssignmentRole, TicketAssignmentRoleDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketAssignmentRoleDto);
    }

    @PutMapping("/ticketAssignmentRole/{idTicketAssignmentRole}")
    @RolesAllowed("Manager")
    public Object UpdateTicketAssignmentRole (@Validated @RequestBody TicketAssignmentRoleDto ticketAssignmentRoleDto, @PathVariable Long idTicketAssignmentRole) {
        TicketAssignmentRole ticketAssignmentRole = modelMapper.map(ticketAssignmentRoleDto, TicketAssignmentRole.class);
        ticketAssignmentRole = ticketAssignmentRoleService.updateTicketAssignmentRole(idTicketAssignmentRole, ticketAssignmentRole);
        ticketAssignmentRoleDto = modelMapper.map(ticketAssignmentRole, TicketAssignmentRoleDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketAssignmentRoleDto);

    }

    @DeleteMapping("/ticketAssignmentRole/{idTicketAssignmentRole}")
    @RolesAllowed("Manager")
    public Object DeleteTicketAssignmentRole(@PathVariable Long idTicketAssignmentRole) {
        ticketAssignmentRoleService.deleteTicketAssignmentRole(idTicketAssignmentRole);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
