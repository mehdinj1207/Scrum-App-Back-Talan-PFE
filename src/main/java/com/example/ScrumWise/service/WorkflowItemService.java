package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Sprint;
import com.example.ScrumWise.model.entity.WorkflowItem;

import java.util.List;

public interface WorkflowItemService
{
    WorkflowItem getWorkflowItemById(Long id);
    List<WorkflowItem> getWorkflowItemListBySprint(Sprint sprint);
    List<WorkflowItem> getDefaultWorkflow(Sprint sprint);
    WorkflowItem getWorkflowItemByNameAndSprint(String name, Sprint sprint);
    WorkflowItem addWorkflowItem(WorkflowItem workflowItem, Sprint sprint);
    WorkflowItem updateWorkflowItemName(Long id, WorkflowItem workflowItem);
    WorkflowItem updateWorkflowItem(Long id, WorkflowItem workflowItem);
    void deleteWorkflowItem(Long id);
    void deleteWorkflowItemBySprint(Sprint sprint);

}
