package com.example.ScrumWise.service.serviceImp;


import com.example.ScrumWise.exception.UserNotFoundException;
import com.example.ScrumWise.message.EmailDetails;
import com.example.ScrumWise.model.entity.Department;
import com.example.ScrumWise.model.entity.User;
import com.example.ScrumWise.repository.UserRepository;
import com.example.ScrumWise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImp implements UserService {

    @Autowired private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;
    @Autowired
    UserRepository userRepository;


    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @Override
    public List<User> getActiveUsers(){
        return userRepository.findUserByStatus(false);
    }
    @Override
    public List<User> getInactiveUsers(){
        return userRepository.findUserByStatus(true);
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found , id :  " + id));
        return user;
    }
    @Override
    public User getUserByEmail(String email) {
        try{
        User user = userRepository.findByEmail(email);
        return user;}
        catch(Exception e){
            return null;
        }
    }
    public User CheckEmailExist(String email) {
        try{
            return userRepository.findByEmail(email);
        }catch (NoSuchElementException e) {
            System.out.println("Email not found!");
            return null;
        }
    }
    @Override
    public List<User> getUserByDepartment(Department department) {

            return (userRepository.findUsersByDepartment(department));

    }
    @Override
    public User createUser(User u) {
        return userRepository.save(u);
    }
    @Override
    public User updateUser(Long id, User user) {
        getUserById(id);
        user.setIdUser(id);
        return userRepository.save(user);
    }
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public String sendSimpleMail(EmailDetails details)
    {
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
    // To send an email with attachment
    public String sendMailWithAttachment(EmailDetails details)
    {
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
    public void setActive(boolean active, Long idUser){
        User user = getUserById(idUser);
        user.setActive(active);
        userRepository.save(user);

    }
}
