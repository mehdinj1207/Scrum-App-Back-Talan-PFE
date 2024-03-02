package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.message.ResponseMessage;
import com.example.ScrumWise.model.entity.User;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.keycloak.admin.client.resource.RoleMappingResource;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class KeycloakService {
    @Value("${keycloak.auth-server-url}")
    private String server_url;

    @Value("${keycloak.realm}")
    private String realm;

    public Object[] createUser(User user){
        ResponseMessage message = new ResponseMessage();
        int statusId = 0;
        try {
            UsersResource usersResource = getUsersResource();
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setUsername(user.getEmail());
            userRepresentation.setEmail(user.getEmail());
            userRepresentation.setFirstName(user.getFirstname());
            userRepresentation.setLastName(user.getLastname());
            userRepresentation.setEnabled(true);

            Response result = usersResource.create(userRepresentation);

            statusId = result.getStatus();

            if(statusId == 201){
                String path = result.getLocation().getPath();
                String userId = path.substring(path.lastIndexOf("/") + 1);
                CredentialRepresentation passwordCredential = new CredentialRepresentation();
                passwordCredential.setTemporary(false);
                passwordCredential.setType(CredentialRepresentation.PASSWORD);
                //passwordCredential.setValue("TaL@N"+user.getCin());
                passwordCredential.setValue("talan");
                usersResource.get(userId).resetPassword(passwordCredential);

                RealmResource realmResource = getRealmResource();
                RoleRepresentation roleRepresentation = realmResource.roles().get(user.getRole()).toRepresentation();
                realmResource.users().get(userId).roles().realmLevel().add(Arrays.asList(roleRepresentation));
                message.setMessage("User created successfully");
            }else if(statusId == 409){
                message.setMessage("Error creating user");
            }else{
                message.setMessage("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return new Object[]{statusId, message};
    }

    //Access the Keycloak Admin API, which is intended for administrative tasks
    private RealmResource getRealmResource(){
        Keycloak kc = KeycloakBuilder.builder().serverUrl(server_url).realm("master").username("admin")
                .password("admin123").clientId("admin-cli").resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
        return kc.realm(realm);
    }

    // UsersResource object is used to manage the users in the Keycloak realm
    private UsersResource getUsersResource(){
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }
    public void deactivateUser(String username) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.search(username);
        if (!userRepresentations.isEmpty()) {
            UserResource userResource = usersResource.get(userRepresentations.get(0).getId());
            UserRepresentation user = userResource.toRepresentation();
            user.setEnabled(false);
            userResource.update(user);
        }
    }
    public void activateUser(String username) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.search(username);
        if (!userRepresentations.isEmpty()) {
            UserResource userResource = usersResource.get(userRepresentations.get(0).getId());
            UserRepresentation user = userResource.toRepresentation();
            user.setEnabled(true);
            userResource.update(user);
        }
    }


    public void changePassword(String username,String newPassword){
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.search(username);
        if (!userRepresentations.isEmpty()) {
            UserResource userResource = usersResource.get(userRepresentations.get(0).getId());
            CredentialRepresentation password = new CredentialRepresentation();
            password.setType(CredentialRepresentation.PASSWORD);
            password.setTemporary(false);
            password.setValue(newPassword);
            userResource.resetPassword(password);
        }
    }







    public void updateKeycloakUser(User user, String email, String oldRole){
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.search(email);
        if (!userRepresentations.isEmpty()) {
            UserResource userResource = usersResource.get(userRepresentations.get(0).getId());
            UserRepresentation userRep = userResource.toRepresentation();
            userRep.setEmail(user.getEmail());
            userRep.setFirstName(user.getFirstname());
            userRep.setLastName(user.getLastname());
            userRep.setUsername(user.getEmail());
            userResource.update(userRep);
            RealmResource realmResource = getRealmResource();
            RoleMappingResource roleMappingsResource = userResource.roles();
            RoleRepresentation newRoleRep = realmResource.roles().get(user.getRole()).toRepresentation();
            RoleRepresentation oldRoleRep = realmResource.roles().get(oldRole).toRepresentation();
            roleMappingsResource.realmLevel().remove(Collections.singletonList(oldRoleRep));
            roleMappingsResource.realmLevel().add(Collections.singletonList(newRoleRep));
        } else {
            System.out.println("User not found in Keycloak");
        }
    }

}