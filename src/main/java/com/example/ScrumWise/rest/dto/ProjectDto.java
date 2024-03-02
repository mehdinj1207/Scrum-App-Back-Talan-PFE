package com.example.ScrumWise.rest.dto;

import com.example.ScrumWise.model.entity.ProjectStatus;
import com.example.ScrumWise.model.entity.Sprint;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDto implements Serializable {
    private Long idProject;
    private String reference;
    private String title;
    private String description;
    private String owner;
    private ProjectStatus projectStatus;
    private LocalDate dateCreation;
    private LocalDate endDate;

    private DepartmentDto department;

    //private List<Sprint> sprints;
}
