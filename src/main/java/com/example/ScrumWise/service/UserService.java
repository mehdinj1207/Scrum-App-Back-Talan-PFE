package com.example.ScrumWise.service;

import com.example.ScrumWise.message.EmailDetails;
import com.example.ScrumWise.model.entity.Department;
import com.example.ScrumWise.model.entity.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    List<User> getUserByDepartment(Department department);
    List<User> getActiveUsers();
    List<User> getInactiveUsers();
    User createUser(User u);
    User updateUser(Long id,User u);
    void deleteUser(Long id);
    String sendSimpleMail(EmailDetails details);
    User CheckEmailExist(String email);
    String sendMailWithAttachment(EmailDetails details);
    void setActive(boolean active, Long idUser);


}
