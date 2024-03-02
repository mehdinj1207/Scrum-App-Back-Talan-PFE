package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.Department;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findByDepartmentIdDepartment(Long idDepartment);
    List<Project> findByProjectStatusAndDepartment(ProjectStatus status, Department department);
}
