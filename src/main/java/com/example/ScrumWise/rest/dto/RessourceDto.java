package com.example.ScrumWise.rest.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RessourceDto implements Serializable {
    private Long idRessource;
    private ProjectDto project;
    private UserDto user;
    private RessourceRoleDto role;
}
