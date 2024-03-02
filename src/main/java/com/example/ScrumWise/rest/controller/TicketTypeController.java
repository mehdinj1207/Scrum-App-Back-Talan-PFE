package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.TicketType;
import com.example.ScrumWise.rest.dto.TicketTypeDto;
import com.example.ScrumWise.service.TicketTypeService;
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
public class TicketTypeController {
    @Autowired
    private TicketTypeService ticketTypeService ;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/ticketTypes")
    @RolesAllowed({"Manager","Consultant"})
    public Object TicketTypeList() {
        List<TicketType> ticketTypes = ticketTypeService.getTicketTypeList();
        Type listType = new TypeToken<List<TicketTypeDto>>() {}.getType() ;
        List<TicketTypeDto> ticketTypeDtos = modelMapper.map(ticketTypes,listType);
        return ResponseEntity.status(HttpStatus.OK).body(ticketTypeDtos);
    }
    @GetMapping("/ticketTypes/{idTicketType}")
    @RolesAllowed({"Manager","Consultant"})
    public Object TicketType(@PathVariable Long idTicketType ) {
        TicketType ticketType = ticketTypeService.getTicketTypeById(idTicketType) ;
        if (ticketType.getIdTicketType()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            TicketTypeDto ticketTypeDto = modelMapper.map(ticketType, TicketTypeDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(ticketTypeDto);
        }
    }

    @PostMapping("/ticketTypes")
    @RolesAllowed("Manager")
    public Object AddTicketType(@Validated @RequestBody TicketTypeDto ticketTypeDto)
    {
        TicketType ticketType = modelMapper.map(ticketTypeDto, TicketType.class);
        ticketType = ticketTypeService.addTicketType(ticketType);
        ticketTypeDto = modelMapper.map(ticketType, TicketTypeDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketTypeDto);
    }

    @PutMapping("/ticketTypes/{idTicketType}")
    @RolesAllowed("Manager")
    public Object UpdateTicketType (@Validated @RequestBody TicketTypeDto ticketTypeDto, @PathVariable Long idTicketType) {
        TicketType ticketType = modelMapper.map(ticketTypeDto, TicketType.class);
        ticketType = ticketTypeService.updateTicketType(idTicketType, ticketType);
        ticketTypeDto = modelMapper.map(ticketType, TicketTypeDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketTypeDto);

    }

    @DeleteMapping("/ticketTypes/{idTicketType}")
    @RolesAllowed("Manager")
    public Object DeleteTicketType(@PathVariable Long idTicketType) {
        ticketTypeService.deleteTicketType(idTicketType);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
