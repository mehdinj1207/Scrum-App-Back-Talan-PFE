package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketStatusRepository extends JpaRepository<TicketStatus,Long> {
    TicketStatus findByName(String name);
}
