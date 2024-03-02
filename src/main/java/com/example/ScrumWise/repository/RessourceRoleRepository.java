package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.RessourceRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RessourceRoleRepository extends JpaRepository<RessourceRole,Long> {
}
