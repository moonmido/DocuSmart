package com.midoShop.midoShop.DocumentService.Controllers;

import com.midoShop.midoShop.DocumentService.Inputs.ReminderRequestInput;
import com.midoShop.midoShop.DocumentService.Models.MyDocument;
import com.midoShop.midoShop.DocumentService.Models.Reminder;
import com.midoShop.midoShop.DocumentService.Services.ReminderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReminder(@RequestBody ReminderRequestInput reminderRequestInput) {
        try {
            Reminder reminder = reminderService.createReminder(reminderRequestInput);
            return ResponseEntity.status(HttpStatus.CREATED).body(reminder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Please provide valid userId and docId!");
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }

    @GetMapping("/expiring")
    public ResponseEntity<?> getExpiringDocuments(@RequestParam String userId) {
        try {
            List<MyDocument> expiringDocs = reminderService.checkExpiringDocuments(userId);
            return ResponseEntity.ok(expiringDocs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Please provide valid userId!");
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documents not found or error retrieving documents!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserReminders(@RequestParam String userId) {
        try {
            List<Reminder> reminders = reminderService.getUserReminders(userId);
            return ResponseEntity.ok(reminders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Please provide valid userId!");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving reminders!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }

    @GetMapping("/document/{docId}")
    public ResponseEntity<?> getReminderByDocId(@RequestParam String userId, @PathVariable Long docId) {
        try {
            Reminder reminder = reminderService.GetReminderByDocId(docId, userId);
            return ResponseEntity.ok(reminder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Please provide valid userId and docId!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reminder not found!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateReminder(@RequestBody ReminderRequestInput reminderRequestInput) {
        try {
            boolean updated = reminderService.updateReminder(reminderRequestInput);
            if (updated) {
                return ResponseEntity.ok("Reminder updated successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reminder not found!");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Please provide valid userId and docId!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }
}
