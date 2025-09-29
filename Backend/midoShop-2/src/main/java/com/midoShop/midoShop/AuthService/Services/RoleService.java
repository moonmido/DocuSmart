package com.midoShop.midoShop.AuthService.Services;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class RoleService {

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;


    public UsersResource GetAllUsersFromKeycloak(){
        return keycloak
                .realm(realm)
                .users();
    }


    public void assignRole(String userId , String roleName){
        if(userId ==null) throw new IllegalArgumentException();
        if(roleName.isEmpty()) roleName = "USER";

        RoleRepresentation representation = keycloak
                .realm(realm)
                .roles()
                .get(roleName)
                .toRepresentation();

        UsersResource usersResource = GetAllUsersFromKeycloak();
        usersResource.get(userId).roles()
                .realmLevel()
                .add(Collections.singletonList(representation));
    }

    public RoleRepresentation GetRoleRepresentation(String roleName){
        return keycloak.realm(realm)
                .roles()
                .get(roleName)
                .toRepresentation();
    }


    public void RemoveAssignedRole(String userId , String roleName){
        if(userId==null ||roleName==null) throw new IllegalArgumentException();

        RoleRepresentation roleRepresentation = GetRoleRepresentation(roleName);
        UsersResource usersResource = GetAllUsersFromKeycloak();
        usersResource.get(userId)
                .roles()
                .realmLevel()
                .remove(List.of(roleRepresentation));

    }




}
