package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.Sprint;
import com.example.ScrumWise.model.entity.WorkflowItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WorkflowItemRepository extends JpaRepository<WorkflowItem,Long> {
    List<WorkflowItem> findWorkflowItemBySprintOrderByOrderNumber(Sprint sprint);
    WorkflowItem findByNameAndSprint(String name, Sprint sprint);
    @Transactional
    void deleteBySprint(Sprint sprint);
    @Query("SELECT MAX(wi.orderNumber) FROM WorkflowItem wi WHERE wi.sprint.id = :id")
    Integer findMaxOrderNumberBySprintId(@Param("id") Long idSprint);
}
