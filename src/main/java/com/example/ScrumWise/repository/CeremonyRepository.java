package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.Ceremony;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CeremonyRepository extends JpaRepository<Ceremony,Long> {
}
