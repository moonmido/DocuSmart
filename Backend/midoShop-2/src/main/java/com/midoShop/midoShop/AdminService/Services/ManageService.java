package com.midoShop.midoShop.AdminService.Services;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageService {

    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;


    public ManageService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }


    public List<UserRepresentation> GetAllUsers(){
        UsersResource users = keycloak.realm(realm)
                .users();
        return users.list();
    }

    public UsersResource GetAll(){
        return keycloak.realm(realm)
                .users();
    }

    public void removeUser(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        UsersResource usersResource = GetAll();

        List<UserRepresentation> users = usersResource.searchByUsername(email, true);

        if (users.isEmpty()) {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }

        String userId = users.get(0).getId();
        usersResource.delete(userId);
    }

    public UserRepresentation GetSpecificUser(String email){
        if(email==null) throw new IllegalArgumentException();

        return keycloak.realm(realm)
                .users()
                .searchByUsername(email,true).get(0);

    }




}
