package com.example.ScrumWise.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkflowItemDto implements Serializable {
    private Long idWorkflowItem;
    private String name;
    private int orderNumber;
    private SprintDto sprint;
    @JsonManagedReference
    private List<TicketDto> tickets;
}
