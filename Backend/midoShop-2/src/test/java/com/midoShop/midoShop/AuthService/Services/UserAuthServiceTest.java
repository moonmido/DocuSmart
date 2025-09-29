package com.midoShop.midoShop.AuthService.Services;


import com.midoShop.midoShop.AuthService.Models.MyUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class UserAuthServiceTest {

    @Autowired
    private UserAuthService userAuthService;

    @Test
    public void CreateUser_failed_myUser_isNull(){
        Assertions.assertThrows(IllegalArgumentException.class , ()-> userAuthService.CreateAccount(null));
    }





}
