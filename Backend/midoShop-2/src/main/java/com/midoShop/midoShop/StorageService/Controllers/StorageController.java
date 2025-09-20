package com.midoShop.midoShop.StorageService.Controllers;


import com.midoShop.midoShop.StorageService.Services.DocStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/storage")
public class StorageController {

    private final DocStorageService docStorageService;

    public StorageController(DocStorageService docStorageService) {
        this.docStorageService = docStorageService;
    }

    @GetMapping("/user-storage-size")
    public ResponseEntity<?> getUserStorageSize(@RequestParam String userId) {
        try {
            Double totalSize = docStorageService.calculateSumOfFilesSizesOfUser(userId);
            return ResponseEntity.ok(totalSize);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Please provide valid userId!");
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error while calculating sizes for user " + userId);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("IO Error occurred while calculating storage size!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }
}
