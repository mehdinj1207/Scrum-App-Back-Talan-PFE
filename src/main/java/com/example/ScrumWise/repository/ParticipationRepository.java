package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.Meet;
import com.example.ScrumWise.model.entity.Participation;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    Participation findByUserAndMeet(User user, Meet meet);
    List<Participation> findByUser(User user);
    List<Participation> findByMeet(Meet meet);
    @Transactional
    void deleteByMeet(Meet meet);
    @Query("SELECT DISTINCT p.meet.project FROM Participation p WHERE p.user = :user")
    List<Project> findDistinctProjectsByUser(@Param("user") User user);
    @Transactional
    @Modifying
    @Query("DELETE FROM Participation p WHERE p.meet.idMeet IN (SELECT m.idMeet FROM Meet m WHERE m.project.idProject = :projectId)")
    void deleteParticipationsByProjectId(@Param("projectId") Long projectId);



}

