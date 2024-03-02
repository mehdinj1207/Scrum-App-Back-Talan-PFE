package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.Sprint;
import com.example.ScrumWise.model.entity.WorkflowItem;
import com.example.ScrumWise.repository.WorkflowItemRepository;
import com.example.ScrumWise.service.SprintService;
import com.example.ScrumWise.service.WorkflowItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WorkflowItemServiceImp implements WorkflowItemService {

    @Autowired
    WorkflowItemRepository workflowItemRepository;
    @Autowired
    SprintService sprintService;
    @Override
    public WorkflowItem getWorkflowItemById(Long id) {
        try{
            return workflowItemRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("WorkflowItem not found!");
            return new WorkflowItem();
        }
    }

    @Override
    public List<WorkflowItem> getWorkflowItemListBySprint(Sprint sprint) {

        return workflowItemRepository.findWorkflowItemBySprintOrderByOrderNumber(sprint);
    }
    @Override
    public WorkflowItem getWorkflowItemByNameAndSprint(String name, Sprint sprint){
        return workflowItemRepository.findByNameAndSprint(name,sprint);
    }

    @Override
    public WorkflowItem addWorkflowItem(WorkflowItem workflowItem, Sprint sprint) {
        workflowItem.setSprint(sprint);
        workflowItem.setOrderNumber(workflowItemRepository.findMaxOrderNumberBySprintId(sprint.getIdSprint())+1);
        return workflowItemRepository.save(workflowItem);
    }

    @Override
    public WorkflowItem updateWorkflowItem(Long id, WorkflowItem workflowItem) {
        workflowItem.setIdWorkflowItem(id);
        return workflowItemRepository.save(workflowItem);
    }
    @Override
    public WorkflowItem updateWorkflowItemName(Long id, WorkflowItem workflowItem) {
        WorkflowItem oldWorkflow= getWorkflowItemById(id);
        oldWorkflow.setName(workflowItem.getName());
        return workflowItemRepository.save(oldWorkflow);
    }
    // update WorkflowItems order before deleting
    @Override
    public void deleteWorkflowItem(Long id) {
        WorkflowItem workflowItem=getWorkflowItemById(id);
        List<WorkflowItem> workflowItems = workflowItemRepository.
                                            findWorkflowItemBySprintOrderByOrderNumber(
                                                    sprintService.getSprint(workflowItem.getSprint().getIdSprint()));
        int n = workflowItems.indexOf(workflowItem);
        workflowItems.remove(workflowItem);
        for (int i=n; i<workflowItems.size();i++){
            workflowItems.get(i).setOrderNumber(workflowItems.get(i).getOrderNumber()-1);
            updateWorkflowItem(workflowItems.get(i).getIdWorkflowItem(),workflowItems.get(i));
        }
        workflowItemRepository.deleteById(id);
    }

    @Override
    public void deleteWorkflowItemBySprint(Sprint sprint) {
        workflowItemRepository.deleteBySprint(sprint);
    }
    @Override
    public List<WorkflowItem> getDefaultWorkflow(Sprint sprint){
        List<WorkflowItem> list=new ArrayList<WorkflowItem>();
        WorkflowItem wf1=new WorkflowItem("To Do",0);
        WorkflowItem wf2=new WorkflowItem("In Progress",1);
        WorkflowItem wf3=new WorkflowItem("Done",2);
        list.add(wf1);
        list.add(wf2);
        list.add(wf3);
        for (int i=0; i<3;i++){
            list.get(i).setSprint(sprint);
        }
        return workflowItemRepository.saveAll(list);
    }
}

