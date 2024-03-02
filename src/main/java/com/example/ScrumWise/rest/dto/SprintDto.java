package com.example.ScrumWise.rest.dto;

import com.example.ScrumWise.model.entity.SprintStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SprintDto implements Serializable {


    private Long idSprint;
    private String name;
    private Long totalEstimation;
    private Long sprintNumber;
    private SprintStatus sprintStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectDto project;
    private String objective;
}
