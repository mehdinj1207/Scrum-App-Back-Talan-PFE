package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.TicketAssignmentRole;
import com.example.ScrumWise.repository.TicketAssignmentRoleRepository;
import com.example.ScrumWise.service.TicketAssignmentRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TicketAssignmentRoleServiceImp implements TicketAssignmentRoleService {
    @Autowired
    TicketAssignmentRoleRepository ticketAssignmentRoleRepository;
    @Override
    public List<TicketAssignmentRole> getTicketAssignmentRoleList() {
        return ticketAssignmentRoleRepository.findAll();
    }

    @Override
    public TicketAssignmentRole getTicketAssignmentRoleById(Long id) {
        try{
            return ticketAssignmentRoleRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("TicketAssignmentRole not found!");
            return new TicketAssignmentRole();
        }
    }

    @Override
    public TicketAssignmentRole addTicketAssignmentRole(TicketAssignmentRole role) {
        return ticketAssignmentRoleRepository.save(role);
    }

    @Override
    public TicketAssignmentRole updateTicketAssignmentRole(Long id, TicketAssignmentRole role) {
        TicketAssignmentRole r =role;
        r.setIdTicketAssignmentRole(id);
        return ticketAssignmentRoleRepository.save(r);
    }

    @Override
    public void deleteTicketAssignmentRole(Long id) {
        ticketAssignmentRoleRepository.deleteById(id);
    }
}
