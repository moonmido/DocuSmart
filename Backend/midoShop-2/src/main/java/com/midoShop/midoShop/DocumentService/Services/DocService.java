package com.midoShop.midoShop.DocumentService.Services;

import com.midoShop.midoShop.AuthService.Services.UserAuthService;
import com.midoShop.midoShop.DocumentService.DTOs.MyDocumentType;
import com.midoShop.midoShop.DocumentService.Inputs.MyDocumentInputInfo;
import com.midoShop.midoShop.DocumentService.Models.MyDocument;
import com.midoShop.midoShop.DocumentService.Prompt.ClassifyDocumentType;
import com.midoShop.midoShop.DocumentService.Repositories.DocRepo;
import jakarta.ws.rs.core.MediaType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.midoShop.midoShop.AuthService.Services.UserAuthService;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DocService {

    private final DocRepo repo;
    private final ChatClient chatClient;

    private final String DIRECTORY_PATH="\\Users\\mac\\Desktop\\Backend\\Store";

    public DocService(DocRepo repo, ChatClient.Builder builder) {
        this.repo = repo;
        this.chatClient = builder.build();
    }

    public void uploadDocument(MultipartFile filetosave , MyDocumentInputInfo myDocumentInputInfo, String userId ) throws IOException {
       if(userId==null) throw new IllegalArgumentException();

    if(myDocumentInputInfo==null) {
        throw new IllegalArgumentException();
    };

    if(filetosave==null) {
        throw new NullPointerException();
    };

    var targetFile = new File(DIRECTORY_PATH + File.separator + filetosave.getOriginalFilename());

    if(!Objects.equals(targetFile.getParent() , DIRECTORY_PATH)) throw new SecurityException();

    Files.copy(filetosave.getInputStream() , targetFile.toPath() , StandardCopyOption.REPLACE_EXISTING);

        MyDocument myDocument = new MyDocument();
        myDocument.setName(myDocumentInputInfo.DocName());
        myDocument.setFilePath(targetFile.getPath());
        myDocument.setIssueDate(myDocumentInputInfo.issueDate());
        myDocument.setExpiryDate(myDocumentInputInfo.ExpiryDate());
        myDocument.getFileSize(filetosave.getSize());
        myDocument.setMimeType(filetosave.getContentType());
        myDocument.setType(myDocumentInputInfo.myDocumentType());
        myDocument.setOriginalFilename(filetosave.getOriginalFilename());
        myDocument.setTags(myDocumentInputInfo.tags());
        myDocument.setCreatedAt(LocalDate.now());
        myDocument.setUserId(userId);
        repo.save(myDocument);

    }

    public List<MyDocument> GetAllDocumentsByUserId(String userId){
        if(userId==null) throw new IllegalArgumentException();
        return repo.findAllByUserId(userId);
    }

    public List<MyDocument> searchDocumentsByName(String name , String userId){
        if(userId==null) throw new IllegalArgumentException();
       return repo.findByNameAndUserId(name,userId);
    }

    public boolean deleteDocument(Long docId , String userId){
        if(userId==null || docId==null) throw new IllegalArgumentException();
        Optional<MyDocument> byId = repo.findByIdAndUserId(docId,userId);
        if(byId.isEmpty()) return false;

        repo.delete(byId.get());
        return true;
    }

    public boolean updateDocument(MyDocumentInputInfo myDocument , Long docId , String userId){
        if(userId==null || docId==null) throw new IllegalArgumentException();

        Optional<MyDocument> byIdAndUserId = repo.findByIdAndUserId(docId, userId);
        if(byIdAndUserId.isEmpty()) return false;
        byIdAndUserId.get().setName(myDocument.DocName());
        byIdAndUserId.get().setTags(myDocument.tags());
        byIdAndUserId.get().setIssueDate(myDocument.issueDate());
        byIdAndUserId.get().setExpiryDate(myDocument.ExpiryDate());
        byIdAndUserId.get().setType(myDocument.myDocumentType());

        repo.save(byIdAndUserId.get());
        return true;
    }

    public MyDocumentType ClassifyDocumentByTypeWithAi(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is null or empty.");
        }

        String documentText;
        try {
            documentText = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read file content", e);
        }

        String userQuery =
                "**Document Text:**\n```\n"
                + documentText + "\n```\n"
                + "**Classification:**";

        return chatClient.prompt()
                .system(ClassifyDocumentType.CLASSIFY_PROMPT)
                .user(u -> u.text(userQuery))
                .call()
                .entity(MyDocumentType.class);

    }

    public File downloadDocument(Long docId , String userId) throws FileNotFoundException {
        if(userId==null || docId==null) throw new IllegalArgumentException();
        Optional<MyDocument> byIdAndUserId = repo.findByIdAndUserId(docId, userId);
        if(byIdAndUserId.isEmpty()) throw new FileNotFoundException();
        String originalFilename = byIdAndUserId.get().getOriginalFilename();
        if(originalFilename==null) throw new FileNotFoundException();
        var fileToDownload = new File(DIRECTORY_PATH + File.separator + originalFilename);
        if(!Objects.equals(fileToDownload.getParent() , DIRECTORY_PATH)) throw new SecurityException();
        if(!fileToDownload.exists()) throw new FileNotFoundException();
        return fileToDownload;
    }



}
