package com.midoShop.midoShop.AuthService.Controllers;

import com.midoShop.midoShop.AuthService.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PutMapping("/add-role/{userId}")
    public ResponseEntity<?> AssignRole(@PathVariable String userId , @RequestParam String roleName){
        try {
            roleService.assignRole(userId,roleName);
            return ResponseEntity.ok("Role = "+roleName+" Assigned");
        }catch (IllegalArgumentException i){
            return ResponseEntity.ofNullable("Empty userId");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }


    @DeleteMapping("/delete-role/{userId}")
    public ResponseEntity<?> RemoveRole(@PathVariable String userId , @RequestParam String roleName){

        try {

            roleService.RemoveAssignedRole(userId,roleName);
            return ResponseEntity.ok("Role = "+roleName+" Deleted");

        }catch (IllegalArgumentException i){
            return ResponseEntity.ofNullable("Empty userId or roleName");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }

    }



}
