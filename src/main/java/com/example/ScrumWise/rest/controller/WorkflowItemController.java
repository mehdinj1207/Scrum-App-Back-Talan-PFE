package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.WorkflowItem;
import com.example.ScrumWise.rest.dto.WorkflowItemDto;
import com.example.ScrumWise.service.SprintService;
import com.example.ScrumWise.service.TicketService;
import com.example.ScrumWise.service.WorkflowItemService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class WorkflowItemController {
    @Autowired
    WorkflowItemService workflowItemService;
    @Autowired
    SprintService sprintService;
    @Autowired
    TicketService ticketService;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/workflow/sprint/{idSprint}")
    @RolesAllowed({"Manager","Consultant"})
    public Object workflowItemListBySprint(@PathVariable Long idSprint) {
        List<WorkflowItem> workflowItems = workflowItemService.getWorkflowItemListBySprint(sprintService.getSprint(idSprint));
        Type listType = new TypeToken<List<WorkflowItemDto>>() {}.getType() ;
        List<WorkflowItemDto> workflowItemDtos = modelMapper.map(workflowItems, listType);
        return ResponseEntity.status(HttpStatus.OK).body(workflowItemDtos);
    }
    @PostMapping("/workflow/sprint/{idSprint}")
    @RolesAllowed("Manager")
    public Object addWorkflowItem(@Validated @RequestBody WorkflowItemDto workflowItemDto, @PathVariable Long idSprint)
    {
        WorkflowItem workflowItem = modelMapper.map(workflowItemDto, WorkflowItem.class);
        workflowItem.setSprint(sprintService.getSprint(idSprint));
        workflowItem = workflowItemService.addWorkflowItem(workflowItem,sprintService.getSprint(idSprint));
        workflowItemDto = modelMapper.map(workflowItem, WorkflowItemDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(workflowItemDto);
    }
    //Edit WorkflowItem Name
    @PutMapping("/workflow/{id}")
    @RolesAllowed("Manager")
    public Object updateWorkflowItem(@Validated @RequestBody WorkflowItemDto workflowItemDto, @PathVariable Long id)
    {
        WorkflowItem workflowItem = modelMapper.map(workflowItemDto,WorkflowItem.class);
        workflowItem= workflowItemService.updateWorkflowItemName(id, workflowItem);
        workflowItemDto = modelMapper.map(workflowItem,WorkflowItemDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(workflowItemDto);
    }

    //Update WorflowItems Order after changing the order of one worfklowItem in the frontEnd
    @PutMapping("/workflowOrder")
    @RolesAllowed("Manager")
    public Map<String, Object> updateWorkflowItemOrder(@RequestBody WorkflowItem[] updatedWorkflowItems)
    {

        //loop inside the updated workflowItems
        for(int i = 0 ; i< updatedWorkflowItems.length;i++){

            //Get the correspending wfItem by ID
            WorkflowItem workflowItem = workflowItemService.
                    getWorkflowItemById(updatedWorkflowItems[i].getIdWorkflowItem());

            //Update its order
            workflowItem.setOrderNumber(i);
            workflowItemService.updateWorkflowItem(workflowItem.getIdWorkflowItem(),workflowItem);
        }

        // create a new map to represent the response
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "order updated Successfully");

        return response;

    }




    /*
        Before deleting a workflowItem, update the related tickets idWorkflowItem
        to the new workflowItem
     */
    @DeleteMapping("/workflow/{idWorkflowItem}/new-workflow/{newIdWorkflowItem}")
    @RolesAllowed("Manager")
    @Transactional
    public Object DeleteWorkflowItem(@PathVariable Long idWorkflowItem,
                                     @PathVariable Long newIdWorkflowItem) {
        ticketService.updateTicketsWorkflowItem(idWorkflowItem,newIdWorkflowItem);
        workflowItemService.deleteWorkflowItem(idWorkflowItem);
        return ResponseEntity.status(HttpStatus.OK).body("workflowItem deleted successfully");
    }

}
