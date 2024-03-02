package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.TicketAssignmentRole;

import java.util.List;

public interface TicketAssignmentRoleService {
    public List<TicketAssignmentRole> getTicketAssignmentRoleList();
    public TicketAssignmentRole getTicketAssignmentRoleById(Long id);
    public TicketAssignmentRole addTicketAssignmentRole(TicketAssignmentRole role);
    public TicketAssignmentRole updateTicketAssignmentRole(Long id,TicketAssignmentRole role);
    public void deleteTicketAssignmentRole(Long id);
}
