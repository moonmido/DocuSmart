package com.midoShop.midoShop.DocumentService.Controllers;

import com.midoShop.midoShop.DocumentService.DTOs.MyDocumentType;
import com.midoShop.midoShop.DocumentService.Inputs.MyDocumentInputInfo;
import com.midoShop.midoShop.DocumentService.Models.MyDocument;
import com.midoShop.midoShop.DocumentService.Services.AiDocService;
import com.midoShop.midoShop.DocumentService.Services.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/api/doc")
public class DocController {


    private final DocService docService;
    private final AiDocService aiDocService;

    public DocController(DocService docService, AiDocService aiDocService) {
        this.docService = docService;
        this.aiDocService = aiDocService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam String userId,
            @RequestBody MyDocumentInputInfo documentInfo) {
        try {
            docService.uploadDocument(file, documentInfo, userId);
            return ResponseEntity.ok().body("Document uploaded successfully!");
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().body("Please provide valid file, document information, and login credentials!");
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Security violation: Invalid file path!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDocuments(@RequestParam String userId) {
        try {
            List<MyDocument> documents = docService.GetAllDocumentsByUserId(userId);
            return ResponseEntity.ok().body(documents);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Please try to login correctly!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchDocuments(
            @RequestParam String name,
            @RequestParam String userId) {
        try {
            List<MyDocument> documents = docService.searchDocumentsByName(name, userId);
            return ResponseEntity.ok().body(documents);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Please try to login correctly!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }

    @DeleteMapping("/delete/{docId}")
    public ResponseEntity<?> deleteDocument(
            @PathVariable Long docId,
            @RequestParam String userId) {
        try {
            boolean deleted = docService.deleteDocument(docId, userId);
            if (deleted) {
                return ResponseEntity.ok().body("Document deleted successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found!");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Please try to login correctly or provide valid document ID!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }

    @PutMapping("/update/{docId}")
    public ResponseEntity<?> updateDocument(
            @PathVariable Long docId,
            @RequestParam String userId,
            @RequestBody MyDocumentInputInfo documentInfo) {
        try {
            boolean updated = docService.updateDocument(documentInfo, docId, userId);
            if (updated) {
                return ResponseEntity.ok().body("Document updated successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found!");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Please try to login correctly or provide valid document ID!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }

    @PostMapping("/classify")
    public ResponseEntity<?> classifyDocument(@RequestParam("file") MultipartFile file) {
        try {
            MyDocumentType documentType = aiDocService.ClassifyDocumentByTypeWithAi(file);
            return ResponseEntity.ok().body(documentType);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Please provide a valid file!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process file content!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }

    @GetMapping("/download/{docId}")
    public ResponseEntity<?> downloadDocument(@RequestParam String userId, @PathVariable Long docId) {
        try {
            File file = docService.downloadDocument(docId, userId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(Files.newInputStream(file.toPath())));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Please try to login, or select Document Correctly!");
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found!");
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Security violation: Invalid file path!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }
}
