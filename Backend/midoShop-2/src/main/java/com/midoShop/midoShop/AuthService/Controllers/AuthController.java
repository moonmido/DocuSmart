package com.midoShop.midoShop.AuthService.Controllers;

import com.midoShop.midoShop.AuthService.AuthExceptions.CannotCreateAccountException;
import com.midoShop.midoShop.AuthService.AuthExceptions.UserNotInDBException;
import com.midoShop.midoShop.AuthService.Models.MyUser;
import com.midoShop.midoShop.AuthService.Services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody MyUser myUser){
        try {
            return userAuthService.CreateAccount(myUser)
                    ? ResponseEntity.ok("Account Created")
                    : ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Can't create your account, please try later.");
        } catch (IllegalArgumentException i) {
            return ResponseEntity.badRequest().body("Please make sure you entered the data correctly.");
        } catch (CannotCreateAccountException c){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username & password should be greater than 8 letters.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
        }
    }

    @PostMapping("/logout/{userId}")
    public ResponseEntity<?> logout(@PathVariable String userId) {
        try {
            userAuthService.Logout(userId);
            return ResponseEntity.ok("User logged out successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("User ID is required.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email) {
        try {
            userAuthService.resetPassword(email);
            return ResponseEntity.ok("Password reset email sent.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Email is required.");
        } catch (UserNotInDBException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not send reset email.");
        }
    }

    @PutMapping("/update-profile/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable String userId, @RequestBody MyUser myUser) {
        try {
            userAuthService.updateProfile(userId, myUser);
            return ResponseEntity.ok("Profile updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input.");
        } catch (UserNotInDBException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not update profile.");
        }
    }


}

