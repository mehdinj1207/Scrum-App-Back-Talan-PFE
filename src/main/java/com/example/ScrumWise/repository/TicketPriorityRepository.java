package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.TicketPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPriorityRepository extends JpaRepository<TicketPriority,Long> {
}
