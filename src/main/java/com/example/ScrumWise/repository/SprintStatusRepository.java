package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.SprintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintStatusRepository extends JpaRepository<SprintStatus,Long> {
    SprintStatus findSprintStatusByName(String name);
}
