package com.example.ScrumWise.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetDto {
    private Long idMeet;
    private String title;
    private String repetition;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private String roomName;
    private String duration;
    private boolean allDay;
    private ProjectDto project;
    private UserDto user;
    private CeremonyDto ceremony;
}

