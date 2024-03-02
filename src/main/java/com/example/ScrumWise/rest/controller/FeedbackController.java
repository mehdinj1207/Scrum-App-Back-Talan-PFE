package com.example.ScrumWise.rest.controller;
import com.example.ScrumWise.model.entity.*;
import com.example.ScrumWise.rest.dto.*;
import com.example.ScrumWise.service.FeedbackReceiversService;
import com.example.ScrumWise.service.FeedbackService;
import com.example.ScrumWise.service.ProjectService;
import com.example.ScrumWise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@CrossOrigin
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;
    @Autowired
    FeedbackReceiversService feedbackReceiversService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/feedbacks/{idFeedback}")
    @RolesAllowed({"Manager","Consultant"})
    public Object getFeedbackById(@PathVariable Long idFeedback ) {
        try{Feedback feedback = feedbackService.getFeedbackById(idFeedback) ;
        if (feedback.getIdFeedback()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            FeedbackDto feedbackDto = modelMapper.map(feedback, FeedbackDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(feedbackDto);
        }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \""+e.getMessage()+"\"}");
        }
    }
    @GetMapping("/feedbacks/sent/user/{idUser}")
    @RolesAllowed({"Manager","Consultant"})
    public Object feedbackListSentByUser(@PathVariable Long idUser) {
        List<Feedback> feedbacks= feedbackService.getFeedbackByUser(userService.getUserById(idUser));
        Type listType = new TypeToken<List<FeedbackDto>>() {}.getType();
        List<FeedbackDto> feedbackDtos = modelMapper.map(feedbacks, listType);
        return ResponseEntity.status(HttpStatus.OK).body(feedbackDtos);
    }
    @GetMapping("/feedbacks/sent/user/{idUser}/project/{idProject}")
    @RolesAllowed({"Manager","Consultant"})
    public Object feedbackListSentByUserAndProject(@PathVariable Long idUser,
                                                   @PathVariable Long idProject) {
        List<Feedback> feedbacks= feedbackService
                .getFeedbacksByProjectAndUser(projectService.getProject(idProject),
                        userService.getUserById(idUser));
        Type listType = new TypeToken<List<FeedbackDto>>() {}.getType();
        List<FeedbackDto> feedbackDtos = modelMapper.map(feedbacks, listType);
        return ResponseEntity.status(HttpStatus.OK).body(feedbackDtos);
    }
    @GetMapping("/feedbacks/received/user/{idUser}")
    @RolesAllowed({"Manager","Consultant"})
    public Object feedbackReceivedForUser(@PathVariable Long idUser ) {
        List<Feedback> feedbacks= feedbackReceiversService
                .getFeedbackReceivedByUser(userService.getUserById(idUser));
        Type listType = new TypeToken<List<FeedbackDto>>() {}.getType();
        List<FeedbackDto> feedbackDtos = modelMapper.map(feedbacks, listType);
        return ResponseEntity.status(HttpStatus.OK).body(feedbackDtos);
    }

    @GetMapping("/feedbacks/objects/received/user/{idUser}")
    @RolesAllowed({"Manager","Consultant"})
    public Object feedbackReceivedForUserFullObject(@PathVariable Long idUser ) {
        List<FeedbackReceivers> feedbackReceivers= feedbackReceiversService
                .getFeedbackReceiversByUser(userService.getUserById(idUser));
        Type listType = new TypeToken<List<FeedbackReceiversDto>>() {}.getType();
        List<FeedbackReceiversDto> feedbackReceiversDtos = modelMapper.map(feedbackReceivers, listType);
        return ResponseEntity.status(HttpStatus.OK).body(feedbackReceiversDtos);
    }

    @PostMapping("/feedbacks/user/{idUser}/project/{idProject}")
    @RolesAllowed({"Manager","Consultant"})
    public Object addFeedback(@Validated @RequestBody FeedbackDto feedbackDto,
                              @PathVariable Long idUser, @PathVariable Long idProject)
    {
        try{
            Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
            feedback.setReplyOnFeedback(null);
            feedback.setProject(projectService.getProject(idProject));
            feedback.setUser(userService.getUserById(idUser));
            feedback.setDateFeedback(LocalDateTime.now());
            feedbackService.addFeedback(feedback);
            feedbackDto = modelMapper.map(feedback, FeedbackDto.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(feedbackDto);
        }
        catch (Exception e){
            e.getMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body("Error Adding feedback");
        }

    }
    @PostMapping("/feedbacks/user/{idUser}/reply-on/{idFeedback}")
    @RolesAllowed({"Manager","Consultant"})
    public Object addFeedbackReply(@Validated @RequestBody FeedbackDto feedbackDto,
                              @PathVariable Long idUser, @PathVariable Long idFeedback)
    {
        try{
            Feedback feedbackRepliedOn= feedbackService.getFeedbackById(idFeedback);
            Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
            feedback.setStar(-1);
            feedback.setReplyOnFeedback(feedbackRepliedOn);
            feedback.setProject(feedbackRepliedOn.getProject());
            feedback.setUser(userService.getUserById(idUser));
            feedback.setDateFeedback(LocalDateTime.now());
            Feedback newFeedBack= feedbackService.addFeedback(feedback);
            feedbackReceiversService.addReceiver(feedback, feedbackRepliedOn.getUser());
            feedbackDto = modelMapper.map(feedback, FeedbackDto.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(feedbackDto);
        }
        catch (Exception e){
            e.getMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body("Error Adding feedback");
        }

    }

    @PutMapping("/feedbacks/{idFeedback}")
    @RolesAllowed({"Manager","Consultant"})
    public Object UpdateFeedback (@Validated @RequestBody FeedbackDto feedbackDto, @PathVariable Long idFeedback) {
        Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
        feedback.setDateEditFeedback(LocalDateTime.now());
        feedback = feedbackService.updateFeedback(idFeedback, feedback);
        feedbackDto = modelMapper.map(feedback, FeedbackDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackDto);

    }

    @DeleteMapping("/feedbacks/{idFeedback}")
    @RolesAllowed({"Manager","Consultant"})
    @Transactional
    public Object deleteFeedback(@PathVariable Long idFeedback) {

        Feedback feedback= feedbackService.getFeedbackById(idFeedback);
        feedbackReceiversService.deleteFeedbackReceiversByFeedback(feedback);
        feedbackService.deleteFeedbackById(idFeedback);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Feedback is successfully deleted!\"}");
    }
    @GetMapping("/feedbacks/receivers/{idFeedback}")
    @RolesAllowed({"Manager","Consultant"})
    public Object feedbackReceiversList(@PathVariable Long idFeedback) {
        List<User> users= feedbackReceiversService
                .getReceiversByFeedback(feedbackService.getFeedbackById(idFeedback));
        Type listType = new TypeToken<List<UserDto>>() {}.getType();
        List<UserDto> userDtos = modelMapper.map(users, listType);
        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }
    @PostMapping("/feedbacks/receivers/{idFeedback}")
    @RolesAllowed({"Manager","Consultant"})
    @Transactional
    public Object addFeedbackReceivers(@Validated @RequestBody List<User> users,
                                       @PathVariable Long idFeedback) {

        Feedback feedback= feedbackService.getFeedbackById(idFeedback);
        feedbackReceiversService.addReceivers(feedback, users);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"receivers added successfully\"}");
    }
    void fixDateTime(Meet meet){
        LocalDateTime updatedDate1 = meet.getStartDate().plusHours(1);
        LocalDateTime updatedDate2 = meet.getEndDate().plusHours(1);
        meet.setStartDate(updatedDate1);
        meet.setEndDate(updatedDate2);
    }
    @GetMapping("/feedbacks/is-important/{isImportant}/{idFeedback}/user/{idUser}")
    @RolesAllowed({"Manager","Consultant"})
    public Object setFeedbackIsImportantForUser(@PathVariable Long idFeedback, @PathVariable Long idUser, @PathVariable Long isImportant) {
        if(isImportant==1) {
            feedbackReceiversService.setIsImportant(userService.getUserById(idUser),
                    feedbackService.getFeedbackById(idFeedback), true);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"feedback is set as important\"}");
        }else{
            feedbackReceiversService.setIsImportant(userService.getUserById(idUser),
                    feedbackService.getFeedbackById(idFeedback), false);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"feedback is set as Not important\"}");
        }
    }
    @GetMapping("/feedbacks/is-read/{isRead}/{idFeedback}/user/{idUser}")
    @RolesAllowed({"Manager","Consultant"})
    public Object setFeedbackIsReadForUser(@PathVariable Long idFeedback, @PathVariable Long idUser, @PathVariable Long isRead) {
        if(isRead==1) {
            feedbackReceiversService.setIsRead(userService.getUserById(idUser),
                    feedbackService.getFeedbackById(idFeedback), true);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"feedback is set as read\"}");
        }else{
            feedbackReceiversService.setIsRead(userService.getUserById(idUser),
                    feedbackService.getFeedbackById(idFeedback), false);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"feedback is set as Not read\"}");
        }
    }
    @PostMapping("/feedbacks/receiver/{idFeedback}/user/{idUser}")
    @RolesAllowed({"Manager","Consultant"})
    @Transactional
    public Object addFeedbackReceivers(@PathVariable Long idUser,
                                       @PathVariable Long idFeedback) {

        Feedback feedback= feedbackService.getFeedbackById(idFeedback);
        User user= userService.getUserById(idUser);
        feedbackReceiversService.addReceiver(feedback, user);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"receivers added successfully\"}");
    }
    @GetMapping("/feedbacks/is-urgent/{isUrgent}/{idFeedback}")
    @RolesAllowed({"Manager","Consultant"})
    public Object setFeedbackIsUrgent(@PathVariable Long idFeedback, @PathVariable Long isUrgent) {
        if(isUrgent==1) {
            feedbackService.setIsUrgent(idFeedback,true);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"feedback is set as urgent\"}");
        }else{
            feedbackService.setIsUrgent(idFeedback,false);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"feedback is set as Not urgent\"}");
        }
    }
    @GetMapping("/feedbacks/replies/{idFeedback}")
    @RolesAllowed({"Manager","Consultant"})
    public Object countReplies(@PathVariable Long idFeedback) {
        Long count= feedbackService.countFeedbacksReplies(feedbackService.getFeedbackById(idFeedback));
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

}

