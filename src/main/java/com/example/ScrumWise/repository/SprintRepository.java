package com.example.ScrumWise.repository;


import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.model.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint,Long> {

    List<Sprint> findSprintByProject(Project project);
    Sprint findTopByProjectOrderByEndDateDesc(Project project);
    @Query("SELECT s.idSprint FROM Sprint s WHERE s.sprintStatus.name = 'In Progress' AND s.project.idProject = :projectId")
    Long findIdBySprintStatusInProgressAndProjectId(@Param("projectId") Long projectId);
    List<Sprint> findByProjectAndSprintStatus_Name(Project project, String sprintStatusName);
    List<Sprint> findByProjectAndSprintStatus_NameNotLike(Project project, String sprintStatusName);


}
