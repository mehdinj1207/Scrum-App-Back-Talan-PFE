package com.example.ScrumWise.rest.dto;

import com.example.ScrumWise.model.entity.Feedback;
import com.example.ScrumWise.model.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackReceiversDto {
    private Long idFeedbackReceivers;
    private FeedbackDto feedback;
    private UserDto user;
    private boolean isRead;
    private boolean isImportant;
}
