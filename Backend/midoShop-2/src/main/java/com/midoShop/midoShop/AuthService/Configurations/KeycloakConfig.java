package com.midoShop.midoShop.AuthService.Configurations;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class KeycloakConfig {


    @Value("${keycloak.client_id}")
    private String client_id;

    @Value("${keycloak.client_secret}")
    private String client_secret;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.server_url}")
    private String server_url;


    @Bean
    public Keycloak keycloak(){
       return KeycloakBuilder.builder()
               .realm(realm)
               .clientId(client_id)
               .clientSecret(client_secret)
               .serverUrl(server_url)
               .grantType("client_credentials")
               .build();
    }

}
