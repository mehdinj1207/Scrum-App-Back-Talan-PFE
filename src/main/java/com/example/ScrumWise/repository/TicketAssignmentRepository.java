package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketAssignmentRepository extends JpaRepository<TicketAssignment,Long> {
    TicketAssignment findTicketAssignmentByTicketAndUser(User user, Ticket ticket);
    List<TicketAssignment> findTicketAssignmentByUser(User user);
    List<TicketAssignment> findTicketAssignmentByTicket(Ticket ticket);
}
