package com.example.ScrumWise.rest.dto;
import com.example.ScrumWise.model.entity.RessourceRole;
import com.example.ScrumWise.model.entity.Ticket;
import com.example.ScrumWise.model.entity.TicketAssignmentRole;
import com.example.ScrumWise.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketAssignmentDto implements Serializable {
    private Long idTicketAssignment;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TicketDto ticket;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserDto user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TicketAssignmentRoleDto ticketAssignmentRole;

}
