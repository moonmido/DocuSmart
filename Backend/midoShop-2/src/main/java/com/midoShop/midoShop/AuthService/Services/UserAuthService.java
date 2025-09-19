package com.midoShop.midoShop.AuthService.Services;

import com.midoShop.midoShop.AuthService.AuthExceptions.CannotCreateAccountException;
import com.midoShop.midoShop.AuthService.AuthExceptions.UserNotInDBException;
import com.midoShop.midoShop.AuthService.Models.MyUser;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserAuthService {

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;


    public boolean CreateAccount(MyUser myUser){
        if(myUser==null) throw new IllegalArgumentException();
        if(myUser.email().length()<8 || myUser.password().length()<8) throw new CannotCreateAccountException();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(myUser.email());
        userRepresentation.setFirstName(myUser.firstname());
        userRepresentation.setLastName(myUser.lastname());
        userRepresentation.setUsername(myUser.email());
        userRepresentation.setEnabled(true);




        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(myUser.password());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        UsersResource usersResource = GetAllUsers();
        Response response = usersResource.create(userRepresentation);
        if(response.getStatus()!=201) return false;

        UserRepresentation userRepresentation1 = GetAllUsers().search(myUser.email(), true).get(0);
        String id = userRepresentation1.getId();
        SendEmailToVerify(id);

        return true;

    }

public UsersResource GetAllUsers(){
        return keycloak.realm(realm)
                .users();
}

public void SendEmailToVerify(String userId){
    UsersResource usersResource = GetAllUsers();
    UserResource userResource = usersResource.get(userId);
    userResource.sendVerifyEmail();
}


public void Logout(String userId){
        if(userId==null) throw new IllegalArgumentException();
    UserResource userResource = GetAllUsers().get(userId);
    userResource.logout();
}

public void resetPassword(String email){
        if(email==null) throw new IllegalArgumentException();
        if(!IsUserExistbyEmail(email)) throw new UserNotInDBException();

    UsersResource usersResource = GetAllUsers();
    UserRepresentation userRepresentation = GetAllUsers().search(email, true).get(0);
    UserResource userResource = usersResource.get(userRepresentation.getId());
    userResource.executeActionsEmail(Collections.singletonList("UPDATE_PASSWORD"));
}


public void updateProfile(String userId, MyUser myUser){

  if(userId==null || myUser.email().isEmpty() || myUser.password().isEmpty()) throw new IllegalArgumentException();
  if(!IsUserExistbyId(userId)) throw new UserNotInDBException();

  UserRepresentation userRepresentation = new UserRepresentation();
  userRepresentation.setEmail(myUser.email());
  userRepresentation.setUsername(myUser.lastname());
  userRepresentation.setFirstName(myUser.firstname());
  userRepresentation.setLastName(myUser.lastname());

    UserResource userResource = GetAllUsers().get(userId);

    userResource.update(userRepresentation);


}





    public boolean IsUserExistbyEmail(String email){
        try {
            GetAllUsers().search(email,true).get(0);
            return true;

        }catch (Exception e){
            return false;
        }

}

    public boolean IsUserExistbyId(String userId){
        try {
            GetAllUsers().get(userId).toRepresentation();
            return true;
        }catch (Exception e){
            return false;
        }

    }

}
