package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.message.EmailDetails;
import com.example.ScrumWise.message.ResponseMessage;
import com.example.ScrumWise.model.entity.User;
import com.example.ScrumWise.rest.dto.UserDto;
import com.example.ScrumWise.service.*;
import com.example.ScrumWise.service.serviceImp.KeycloakService;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import javax.annotation.security.RolesAllowed;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CountryService countryService;

    @Autowired
    private RessourceService ressourceService;
    @Autowired
    private TicketAssignmentService ticketAssignmentService;

    @Autowired
    private KeycloakService keycloakService;
    @Autowired
    private ModelMapper modelMapper ;

    private Keycloak keycloak;

    //Get all Users
    @GetMapping("/users")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public List<User> getAllUsers(){
        return userService.getUsers();
    }
    @GetMapping("/users/active")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public List<User> getActiveUsers(){
        return userService.getActiveUsers();
    }

    @GetMapping("/users/department/{idDepartment}")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public List<User> getUserByDepartment(@PathVariable Long idDepartment){
        return userService.getUserByDepartment(departmentService.getDepartmentById(idDepartment));
    }
    @GetMapping("/users/inactive")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public List<User> getInactiveUsers(){
        return userService.getInactiveUsers();
    }

    //Get User by ID
    @GetMapping("/users/{id}")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public Object getUserById(@PathVariable Long id ){
        User user= userService.getUserById(id);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    //Get User by Email
    @GetMapping("/users/byEmail/{email}")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public Object getUserByEmail(@PathVariable String email ){
        User user= userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //Create user
    @PostMapping("/users/department/{idDepartment}")
    public Object createUser(@RequestBody User user, @PathVariable Long idDepartment) {
        user.setDepartment(departmentService.getDepartmentById(idDepartment));
        user = userService.createUser(user);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
    @PostMapping("/users/department/{idDepartment}/country/{iso}")
    @RolesAllowed({"Human Ressources","Manager"})
    public Object createNewUser(@RequestBody User user, @PathVariable Long idDepartment,
                                @PathVariable String iso) {
        user.setCountry(countryService.getCountryByIso(iso));
        user.setDepartment(departmentService.getDepartmentById(idDepartment));
        user = userService.createUser(user);
        //Create user in keycloak
        Object[] obj = keycloakService.createUser(user);
        int status = (int) obj[0];
        ResponseMessage message = (ResponseMessage) obj[1];
        UserDto userDto = modelMapper.map(user, UserDto.class);
        if(status==200||status==201) {
            EmailDetails email=new EmailDetails(user);
            userService.sendMailWithAttachment(email);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        }
        return ResponseEntity.status(status).body(message);
    }

    //Update User By ID
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id ,@RequestBody User userDetails ) {
        User user = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/users/{id}/department/{idDepartment}/country/{iso}")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public ResponseEntity<User> updateUserWithDepartment(@RequestBody User userDetails,
                                                         @PathVariable Long id,
                                                         @PathVariable Long idDepartment,
                                                         @PathVariable String iso) {
        User userBeforeUpdate=userService.getUserById(id);
        keycloakService.updateKeycloakUser(userDetails,userBeforeUpdate.getEmail(),userBeforeUpdate.getRole());
        userDetails.setCountry(countryService.getCountryByIso(iso));
        userDetails.setDepartment(departmentService.getDepartmentById(idDepartment));
        User user = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/uploadgeturl")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public ResponseEntity<byte[]> uploadImageGetUrl(@RequestParam("file") MultipartFile file) throws IOException {
        User user= new User();
        user.setFirstname(file.getOriginalFilename());
        user.setLastname(file.getContentType());
        user.setData(file.getBytes());
        byte[] imageBytes = file.getBytes();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
    //set Image to user
    @PostMapping("/users/upload/{id}")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public void updateUserSetImage(@RequestParam("file") MultipartFile file, @PathVariable Long id ) {
        try {
            User user=userService.getUserById(id);
            user.setData(file.getBytes());
            userService.updateUser(id, user);
            // save blob to database
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Delete User by ID

    @DeleteMapping("/users/{id}")
    @RolesAllowed({"Human Ressources","Manager"})
    public Object DeleteEmployee(@PathVariable Long id) {
        User userToDelete = userService.getUserById(id);
        userToDelete.setStatus(true);
        userService.updateUser(id, userToDelete);
        ressourceService.deleteRessourcesByUser(userToDelete);
        ticketAssignmentService.deleteTicketAssignmentByUser(userToDelete);
        keycloakService.deactivateUser(userToDelete.getEmail());
        String msg= "User "+ userToDelete.getFirstname() +" "+userToDelete.getLastname()+" is Deleted";
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }


    //Change User's password by username
    @PostMapping("/users/changePassword/{username}")
    public void changePassword(@PathVariable String username ,@RequestBody String password) {

        keycloakService.changePassword(username,password);

    }
    @GetMapping("/users/check-email/{email}")
    public Object User(@PathVariable String email ) {
        if (userService.CheckEmailExist(email)==null)
        return ResponseEntity.status(HttpStatus.OK).body(false);
        else {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
    }
    @GetMapping("/users/activate/{idUser}")
    public Object activateUser(@PathVariable Long idUser ) {
        User user=userService.getUserById(idUser);
        user.setStatus(false);
        userService.updateUser(idUser, user);
        keycloakService.activateUser(user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User "+user.getEmail()+" is activated!\"}");
    }
    @GetMapping("/users/logged-in/{idUser}")
    public void loggedInUser(@PathVariable Long idUser ) {
        User user = userService.getUserById(idUser);
        user.setActive(true);
        userService.updateUser(idUser, user);
    }

    @GetMapping("/users/logged-out/{idUser}")
    public void loggedOutUser(@PathVariable Long idUser ) {
        User user=userService.getUserById(idUser);
        user.setActive(false);
        userService.updateUser(idUser, user);}

   
}
