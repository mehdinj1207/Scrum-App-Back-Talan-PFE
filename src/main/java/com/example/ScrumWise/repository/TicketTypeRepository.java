package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType,Long> {
}
