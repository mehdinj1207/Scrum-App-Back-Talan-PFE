package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.RessourceRole;
import com.example.ScrumWise.rest.dto.RessourceRoleDto;
import com.example.ScrumWise.service.RessourceRoleService;

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
public class RessourceRoleController {
    @Autowired
    private RessourceRoleService ressourceRoleService ;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/ressourceRoles")
    @RolesAllowed({"Manager","Consultant"})
    public Object RessourceRoleList() {
        List<RessourceRole> ressourceRoles = ressourceRoleService.getRessourceRoleList();
        Type listType = new TypeToken<List<RessourceRoleDto>>() {}.getType() ;
        List<RessourceRoleDto> ressourceRoleDtos = modelMapper.map(ressourceRoles,listType);
        return ResponseEntity.status(HttpStatus.OK).body(ressourceRoleDtos);
    }
    @GetMapping("/ressourceRoles/{idRessourceRole}")
    @RolesAllowed({"Manager","Consultant"})
    public Object RessourceRole(@PathVariable Long idRessourceRole ) {
        RessourceRole ressourceRole = ressourceRoleService.getRessourceRoleById(idRessourceRole) ;
        if (ressourceRole.getIdRessourceRole()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            RessourceRoleDto ressourceRoleDto = modelMapper.map(ressourceRole, RessourceRoleDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(ressourceRoleDto);
        }
    }

    @PostMapping("/ressourceRoles")
    @RolesAllowed("Manager")
    public Object AddRessourceRole(@Validated @RequestBody RessourceRoleDto ressourceRoleDto)
    {
        RessourceRole ressourceRole = modelMapper.map(ressourceRoleDto, RessourceRole.class);
        ressourceRole = ressourceRoleService.addRessourceRole(ressourceRole);
        ressourceRoleDto = modelMapper.map(ressourceRole, RessourceRoleDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ressourceRoleDto);
    }

    @PutMapping("/ressourceRoles/{idRessourceRole}")
    @RolesAllowed("Manager")
    public Object UpdateRessourceRole (@Validated @RequestBody RessourceRoleDto ressourceRoleDto, @PathVariable Long idRessourceRole) {
        RessourceRole ressourceRole = modelMapper.map(ressourceRoleDto, RessourceRole.class);
        ressourceRole = ressourceRoleService.updateRessourceRole(idRessourceRole, ressourceRole);
        ressourceRoleDto = modelMapper.map(ressourceRole, RessourceRoleDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(ressourceRoleDto);

    }

    @DeleteMapping("/ressourceRoles/{idRessourceRole}")
    @RolesAllowed("Manager")
    public Object DeleteRessourceRole(@PathVariable Long idRessourceRole) {
        ressourceRoleService.deleteRessourceRole(idRessourceRole);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
