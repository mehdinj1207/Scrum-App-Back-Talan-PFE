package com.example.ScrumWise.message;

import com.example.ScrumWise.model.entity.Meet;
import com.example.ScrumWise.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor

// Class
public class EmailDetails {

    // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
    public EmailDetails(User user){
        this.recipient= user.getEmail();
        this.subject="Authentication Credentials to ScrumWise";
        this.msgBody="Dear "+user.getFirstname()+" "+user.getLastname()+",\n" +
                "\n" +
                "Welcome to our application! We are excited to have you onboard and we hope you are settling in well.\n" +
                "\n" +
                "As part of your onboarding process, we would like to provide you with your login credentials so that you can start communicating with other members of the team.\n" +
                "\n" +
                "Your email address is "+user.getEmail()+" and your temporary password is TaL@N"+user.getCin()+". Please note that you will be required to change your password upon logging in for the first time.\n" +
                "\n" +
                "If you have any questions or concerns, please don't hesitate to reach out to us. We are here to help and support you.\n" +
                "\n" +
                "Best regards,\n" +
                "ScrumWise-Talan";
        this.attachment="src/main/resources/static/assets/images/ScrumWise.png";
    }
    public EmailDetails(User user, Meet meet){
        this.recipient=user.getEmail();
        this.subject="Join Meeting Invitation";
        this.msgBody="Dear "+user.getFirstname()+" "+user.getLastname()+",\n" +
                "\n" +
                "I would like to invite you to a meeting on "+meet.getStartDate().toLocalDate()+" to discuss "+meet.getDescription()+". We will be meeting via SCRUMWISE.\n" +
                "\n" +
                "Please let me know if you are available to attend the meeting. If you have any questions or concerns, feel free to contact me.\n" +
                "\n" +
                "Thank you for considering this invitation" +
                "\n" +
                "LINK:<a href='http://localhost:4200/customdash/meet/" + meet.getRoomName() + "'>" + "http://localhost:4200/customdash/meet/" + meet.getRoomName() + "</a>" +
                "\n" +
                "Best regards,\n" +
                "ScrumWise-Talan -Oumaima Ben Elhaj Ali ";
        this.attachment="src/main/resources/static/assets/images/ScrumWise.png";
    }

}
