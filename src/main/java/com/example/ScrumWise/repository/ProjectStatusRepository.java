package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus,Long> {
    ProjectStatus findByStatusName(String name);
}
