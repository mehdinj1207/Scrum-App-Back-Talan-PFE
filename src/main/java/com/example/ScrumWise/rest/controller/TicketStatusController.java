package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.TicketStatus;
import com.example.ScrumWise.rest.dto.TicketStatusDto;
import com.example.ScrumWise.service.TicketStatusService;
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
public class TicketStatusController {
    @Autowired
    private TicketStatusService ticketStatusService ;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/ticketStatus")
    @RolesAllowed({"Manager","Consultant"})
    public Object TicketStatusList() {
        List<TicketStatus> ticketStatuss = ticketStatusService.getTicketStatusList();
        Type listType = new TypeToken<List<TicketStatusDto>>() {}.getType() ;
        List<TicketStatusDto> ticketStatusDtos = modelMapper.map(ticketStatuss,listType);
        return ResponseEntity.status(HttpStatus.OK).body(ticketStatusDtos);
    }
    @GetMapping("/ticketStatus/{idTicketStatus}")
    @RolesAllowed({"Manager","Consultant"})
    public Object TicketStatus(@PathVariable Long idTicketStatus ) {
        TicketStatus ticketStatus = ticketStatusService.getTicketStatusById(idTicketStatus) ;
        if (ticketStatus.getIdTicketStatus()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            TicketStatusDto ticketStatusDto = modelMapper.map(ticketStatus, TicketStatusDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(ticketStatusDto);
        }
    }

    @PostMapping("/ticketStatus")
    @RolesAllowed("Manager")
    public Object AddTicketStatus(@Validated @RequestBody TicketStatusDto ticketStatusDto)
    {
        TicketStatus ticketStatus = modelMapper.map(ticketStatusDto, TicketStatus.class);
        ticketStatus = ticketStatusService.addTicketStatus(ticketStatus);
        ticketStatusDto = modelMapper.map(ticketStatus, TicketStatusDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketStatusDto);
    }

    @PutMapping("/ticketStatus/{idTicketStatus}")
    @RolesAllowed("Manager")
    public Object UpdateTicketStatus (@Validated @RequestBody TicketStatusDto ticketStatusDto, @PathVariable Long idTicketStatus) {
        TicketStatus ticketStatus = modelMapper.map(ticketStatusDto, TicketStatus.class);
        ticketStatus = ticketStatusService.updateTicketStatus(idTicketStatus, ticketStatus);
        ticketStatusDto = modelMapper.map(ticketStatus, TicketStatusDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketStatusDto);

    }

    @DeleteMapping("/ticketStatus/{idTicketStatus}")
    @RolesAllowed("Manager")
    public Object DeleteTicketStatus(@PathVariable Long idTicketStatus) {
        ticketStatusService.deleteTicketStatus(idTicketStatus);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
