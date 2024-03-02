package com.example.ScrumWise.service.serviceImp;


import com.example.ScrumWise.message.EmailDetails;
import com.example.ScrumWise.model.entity.Meet;
import com.example.ScrumWise.model.entity.Project;
import com.example.ScrumWise.repository.MeetRepository;
import com.example.ScrumWise.service.MeetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;
import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class MeetServiceImp implements MeetService {
    @Autowired
    private MeetRepository meetRepository ;
    @Value("${spring.mail.username}") private String sender;

    @Autowired private JavaMailSender javaMailSender;
    @Override
    public Meet getMeetById(Long id) {
        try{
            return meetRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Meet not found!");
            return new Meet();
        }
    }

    @Override
    public List<Meet> getMeetList() {
        return meetRepository.findAll();
    }

    @Override
    public Meet addMeet(Meet meet) {
        return meetRepository.save(meet);
    }

    @Override
    public Meet updateMeet(Long id, Meet meet) {
        Meet oldMeet=meetRepository.findById(id).get();
        oldMeet.setStartDate(meet.getStartDate());
        oldMeet.setEndDate(meet.getEndDate());
        oldMeet.setDescription(meet.getDescription());
        oldMeet.setCeremony(meet.getCeremony());
        oldMeet.setProject(meet.getProject());
        oldMeet.setTitle(meet.getTitle());
        return meetRepository.save(oldMeet);
    }
    @Override
    public List<Project> getDistinctProjectsByUserId(Long userId){
        return meetRepository.findDistinctProjectsByUserId(userId);
    }
    @Override
    public void deleteMeet(Long id) {
        meetRepository.deleteById(id);
    }
    @Override
    public String generateRoomName(){

        String roomName=RandomRoomName();
        while(meetRepository.findByRoomName(roomName)!=null){
            roomName=RandomRoomName();
        }
        return roomName;
    }
    public String RandomRoomName() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 9; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        String roomName = sb.toString();
        StringBuilder formattedRoomName = new StringBuilder();
        // Insert dashes at the appropriate positions
        for (int i = 0; i < roomName.length(); i += 3) {
            formattedRoomName.append(roomName.substring(i, Math.min(i + 3, roomName.length())));
            if (i + 3 < roomName.length()) {
                formattedRoomName.append("-");
            }
        }
        return formattedRoomName.toString();
    }
    @Override
    public String sendMeetInvitation(EmailDetails details){
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }
        catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }
    @Override
    public void deleteMeetsByProject(Project project){
        meetRepository.deleteByProject(project);
    }
}

