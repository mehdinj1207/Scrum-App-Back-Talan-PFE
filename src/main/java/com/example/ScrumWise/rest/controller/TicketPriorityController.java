package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.TicketPriority;
import com.example.ScrumWise.rest.dto.TicketPriorityDto;
import com.example.ScrumWise.service.TicketPriorityService;
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
public class TicketPriorityController {
    @Autowired
    private TicketPriorityService ticketPriorityService ;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/ticketPriorities")
    @RolesAllowed({"Manager","Consultant"})
    public Object TicketPriorityList() {
        List<TicketPriority> ticketPriorities = ticketPriorityService.getTicketPriorityList();
        Type listType = new TypeToken<List<TicketPriorityDto>>() {}.getType() ;
        List<TicketPriorityDto> ticketPriorityDtos = modelMapper.map(ticketPriorities,listType);
        return ResponseEntity.status(HttpStatus.OK).body(ticketPriorityDtos);
    }
    @GetMapping("/ticketPriorities/{idTicketPriority}")
    @RolesAllowed({"Manager","Consultant"})
    public Object TicketPriority(@PathVariable Long idTicketPriority ) {
        TicketPriority ticketPriority = ticketPriorityService.getTicketPriorityById(idTicketPriority) ;
        if (ticketPriority.getIdTicketPriority()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            TicketPriorityDto ticketPriorityDto = modelMapper.map(ticketPriority, TicketPriorityDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(ticketPriorityDto);
        }
    }

    @PostMapping("/ticketPriorities")
    @RolesAllowed("Manager")
    public Object AddTicketPriority(@Validated @RequestBody TicketPriorityDto ticketPriorityDto)
    {
        TicketPriority ticketPriority = modelMapper.map(ticketPriorityDto, TicketPriority.class);
        ticketPriority = ticketPriorityService.addTicketPriority(ticketPriority);
        ticketPriorityDto = modelMapper.map(ticketPriority, TicketPriorityDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketPriorityDto);
    }

    @PutMapping("/ticketPriorities/{idTicketPriority}")
    @RolesAllowed("Manager")
    public Object UpdateTicketPriority (@Validated @RequestBody TicketPriorityDto ticketPriorityDto, @PathVariable Long idTicketPriority) {
        TicketPriority ticketPriority = modelMapper.map(ticketPriorityDto, TicketPriority.class);
        ticketPriority = ticketPriorityService.updateTicketPriority(idTicketPriority, ticketPriority);
        ticketPriorityDto = modelMapper.map(ticketPriority, TicketPriorityDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketPriorityDto);

    }

    @DeleteMapping("/ticketPriorities/{idTicketPriority}")
    @RolesAllowed("Manager")
    public Object DeleteTicketPriority(@PathVariable Long idTicketPriority) {
        ticketPriorityService.deleteTicketPriority(idTicketPriority);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
