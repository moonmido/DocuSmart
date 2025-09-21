package com.midoShop.midoShop.AuthService.Controllers;

import com.midoShop.midoShop.AuthService.AuthExceptions.UserNotInDBException;
import com.midoShop.midoShop.AuthService.Services.UserAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {


    private final UserAuthService userAuthService;


    public ProfileController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping("/get-profile/{userId}")
    public ResponseEntity<?> GetUserProfile(@PathVariable String userId){

        try {
            return ResponseEntity.ok(userAuthService.GetUserInformations(userId));
        }catch (IllegalArgumentException i){
            return ResponseEntity.badRequest().body("Empty userId");
        }catch (UserNotInDBException u){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You must sign in , or create account");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }

    }


}
