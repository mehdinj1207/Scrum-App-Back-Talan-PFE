package com.example.ScrumWise.rest.dto;

import com.example.ScrumWise.model.entity.Feedback;
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
public class FeedbackDto {
    private Long idFeedback;
    private String content;
    private int star;
    private boolean isUrgent;
    private LocalDateTime dateFeedback;
    private LocalDateTime dateEditFeedback;
    private ProjectDto project;
    private UserDto user;
    private FeedbackDto replyOnFeedback;
}

