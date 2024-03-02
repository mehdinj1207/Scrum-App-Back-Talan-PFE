package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.Epic;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpicRepository extends JpaRepository<Epic,Long> {
    List<Epic> findEpicByProject(Project project);
}
