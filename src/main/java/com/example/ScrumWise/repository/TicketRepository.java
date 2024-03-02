package com.example.ScrumWise.repository;


import com.example.ScrumWise.model.entity.Epic;
import com.example.ScrumWise.model.entity.Sprint;
import com.example.ScrumWise.model.entity.Ticket;
import com.example.ScrumWise.model.entity.WorkflowItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {


    List<Ticket> findTicketByEpic(Epic epic);

    @Query("SELECT t FROM Ticket t JOIN t.workflowItem wi JOIN wi.sprint s WHERE s.idSprint = :sprintId")
    List<Ticket> findBySprintId(@Param("sprintId") Long sprintId);

    List<Ticket> findByWorkflowItem(WorkflowItem workflowItem);
    @Modifying
    @Query
            ("UPDATE Ticket t SET t.workflowItem.idWorkflowItem = :newId WHERE t.workflowItem.idWorkflowItem = :oldId")
    void updateTicketWorkflowItemId(@Param("oldId") Long oldId, @Param("newId") Long newId);

    List<Ticket> findByWorkflowItem_Sprint_IdSprintAndWorkflowItem_NameNot(Long sprintId, String workflowItemName);
}
