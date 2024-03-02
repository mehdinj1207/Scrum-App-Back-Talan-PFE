package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.Meet;
import com.example.ScrumWise.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetRepository extends JpaRepository<Meet,Long> {
    @Query("SELECT DISTINCT m.project FROM Meet m WHERE m.user.idUser = :userId")
    List<Project> findDistinctProjectsByUserId(@Param("userId") Long userId);

    Meet findByRoomName(String roomName);
    void deleteByProject(Project project);
}

