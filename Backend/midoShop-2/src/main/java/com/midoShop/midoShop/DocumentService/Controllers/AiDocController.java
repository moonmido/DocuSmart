package com.midoShop.midoShop.DocumentService.Controllers;

import com.midoShop.midoShop.DocumentService.DTOs.MyDocumentType;
import com.midoShop.midoShop.DocumentService.Services.AiDocService;
import jakarta.ws.rs.NotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RequestMapping("/api/ai")
@RestController
public class AiDocController {

    private final AiDocService aiDocService;


    public AiDocController(AiDocService aiDocService) {
        this.aiDocService = aiDocService;
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


    @GetMapping("/resume/{docId}")
    public ResponseEntity<?> DocumentResume(@PathVariable Long docId){
        try {
            return ResponseEntity.ok(aiDocService.ResumePdfDocumentWithAi(docId));
        }catch (IllegalArgumentException i){
            return ResponseEntity.badRequest().body("Please provide a valid file!");
        }catch (FileNotFoundException fe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found ");
        }catch (NotAllowedException n){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("File Format must be PDF");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }

}
