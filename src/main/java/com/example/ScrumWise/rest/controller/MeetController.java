package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.*;
import com.example.ScrumWise.rest.dto.*;
import com.example.ScrumWise.service.*;
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
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
public class MeetController {
    @Autowired
    private MeetService meetService ;
    @Autowired
    private ParticipationService participationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CeremonyService ceremonyService;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/meets/user/{idUser}")
    @RolesAllowed({"Manager","Consultant"})
    public Object MeetList(@PathVariable Long idUser) {
        User user = userService.getUserById(idUser);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        List<Meet> meets = participationService.getMeetingByParticipant(user);
        if (meets == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No meetings found for the user");
        }

        Type listType = new TypeToken<List<MeetDto>>() {}.getType();
        List<MeetDto> meetDtos = modelMapper.map(meets, listType);
        return ResponseEntity.status(HttpStatus.OK).body(meetDtos);
    }
    @GetMapping("/meets/user/{idUser}/projects")
    @RolesAllowed({"Manager","Consultant"})
    public Object ProjectListByUserInMeet(@PathVariable Long idUser) {
        List<Project> projects = participationService.getDistinctProjectsByUserId(userService.getUserById(idUser));
        Type listType = new TypeToken<List<ProjectDto>>() {}.getType();
        List<ProjectDto> projectDtos = modelMapper.map(projects,listType);
        return ResponseEntity.status(HttpStatus.OK).body(projectDtos);
    }
    @GetMapping("/meets/{idMeet}")
    @RolesAllowed({"Manager","Consultant"})
    public Object Meet(@PathVariable Long idMeet ) {
        Meet meet = meetService.getMeetById(idMeet) ;
        if (meet.getIdMeet()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            MeetDto meetDto = modelMapper.map(meet, MeetDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(meetDto);
        }
    }

    @PostMapping("/meets/user/{idUser}/project/{idProject}/ceremony/{idCeremony}")
    @RolesAllowed({"Manager","Consultant"})
    public Object AddMeet(@Validated @RequestBody MeetDto meetDto, @PathVariable Long idUser
            , @PathVariable Long idProject, @PathVariable Long idCeremony)
    {
        try{
            Meet meet = modelMapper.map(meetDto, Meet.class);
            User user=userService.getUserById(idUser);
            meet.setProject(projectService.getProject(idProject));
            meet.setCeremony(ceremonyService.getCeremonyById(idCeremony));
            meet.setUser(userService.getUserById(idUser));
            fixDateTime(meet);
            meet.setRoomName(meetService.generateRoomName());
            meet = meetService.addMeet(meet);
            //------ sending Mail-------------
            /*EmailDetails email=new EmailDetails(user, meet);
            meetService.sendMeetInvitation(email);*/
            participationService.addParticipation(meet,user);
            meetDto = modelMapper.map(meet, MeetDto.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(meetDto);
        }
        catch (Exception e){
            e.getMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body("Error Adding meet");
        }

    }
    @PostMapping("/meets/participants/{idMeet}")
    @RolesAllowed({"Manager","Consultant"})
    @Transactional
    public Object AddParticipantsForMeet(@Validated @RequestBody List<User> users,
                                         @PathVariable Long idMeet) {

        Meet meet= meetService.getMeetById(idMeet);
        User user;
        for(int i=0; i<users.size();i++){
            user=userService.getUserById(users.get(i).getIdUser());
            participationService.addParticipation(meet,user);
            //------ sending Mail-------------
            /*EmailDetails email=new EmailDetails(user, meet);
            meetService.sendMeetInvitation(email);*/
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"participants added successfully\"}");
    }

    @PutMapping("/meets/{idMeet}/project/{idProject}/ceremony/{idCeremony}")
    @RolesAllowed({"Manager","Consultant"})
    public Object UpdateMeet (@Validated @RequestBody MeetDto meetDto, @PathVariable Long idMeet
            , @PathVariable Long idProject, @PathVariable Long idCeremony) {
        Meet meet = modelMapper.map(meetDto, Meet.class);
        meet.setProject(projectService.getProject(idProject));
        meet.setCeremony(ceremonyService.getCeremonyById(idCeremony));
        fixDateTime(meet);
        meet = meetService.updateMeet(idMeet, meet);
        meetDto = modelMapper.map(meet, MeetDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(meetDto);

    }

    @DeleteMapping("/meets/{idMeet}")
    @RolesAllowed({"Manager","Consultant"})
    @Transactional
    public Object DeleteMeet(@PathVariable Long idMeet) {

        Meet meetToDelete = meetService.getMeetById(idMeet);
        participationService.deleteParticipationByMeet(meetToDelete);
        meetService.deleteMeet(idMeet);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Meet "+meetToDelete.getTitle()+" is deleted!\"}");
    }
    @GetMapping("/meets/ceremonies")
    @RolesAllowed({"Manager","Consultant"})
    public Object MeetCeremoniesList() {
        List<Ceremony> ceremonies = ceremonyService.getCeremonies();
        Type listType = new TypeToken<List<CeremonyDto>>() {}.getType();
        List<CeremonyDto> ceremonyDtos = modelMapper.map(ceremonies,listType);
        return ResponseEntity.status(HttpStatus.OK).body(ceremonyDtos);
    }
    @GetMapping("/meets/participants/{idMeet}")
    @RolesAllowed({"Manager","Consultant"})
    public Object MeetCeremoniesList(@PathVariable Long idMeet) {
        List<User> users = participationService.getParticipants(meetService.getMeetById(idMeet));
        Type listType = new TypeToken<List<UserDto>>() {}.getType();
        List<UserDto> userDtos = modelMapper.map(users,listType);
        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

    @PostMapping("/meets/update/participants/{idMeet}")
    @RolesAllowed({"Manager","Consultant"})
    @Transactional
    public Object UpdateMeetParticipants(@Validated @RequestBody List<User> users,
                                         @PathVariable Long idMeet) {
        User user;
        Meet meet= meetService.getMeetById(idMeet);
        participationService.deleteParticipationByMeet(meet);
        participationService.addParticipation(meet,meet.getUser());
        for(int i=0; i<users.size();i++){
            user=userService.getUserById(users.get(i).getIdUser());
            participationService.addParticipation(meet,user);
            //------ sending Mail-------------
            /*EmailDetails email=new EmailDetails(user, meet);
            meetService.sendMeetInvitation(email);*/
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"participants updated successfully\"}");
    }
    void fixDateTime(Meet meet){
        LocalDateTime updatedDate1 = meet.getStartDate().plusHours(1);
        LocalDateTime updatedDate2 = meet.getEndDate().plusHours(1);
        meet.setStartDate(updatedDate1);
        meet.setEndDate(updatedDate2);
    }

}
