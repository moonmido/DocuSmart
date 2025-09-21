package com.midoShop.midoShop.AdminService.Controllers;

import com.midoShop.midoShop.AdminService.Services.ManageService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminController {

    private final ManageService manageService;



    public AdminController(ManageService manageService) {
        this.manageService = manageService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserRepresentation> users = manageService.GetAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching users: " + e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getSpecificUser(@PathVariable String email) {
        try {
            UserRepresentation user = manageService.GetSpecificUser(email);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with email: " + email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching user: " + e.getMessage());
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> removeUser(@PathVariable String email) {
        try {
            manageService.removeUser(email);
            return ResponseEntity.ok("User with email " + email + " deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + e.getMessage());
        }
    }
}

