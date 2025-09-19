package com.midoShop.midoShop.DocumentService.Services;

import com.midoShop.midoShop.DocumentService.DTOs.MyDocumentType;
import com.midoShop.midoShop.DocumentService.Inputs.ReminderRequestInput;
import com.midoShop.midoShop.DocumentService.Models.MyDocument;
import com.midoShop.midoShop.DocumentService.Outputs.PdfResumeOutput;
import com.midoShop.midoShop.DocumentService.Prompt.ClassifyDocumentType;
import com.midoShop.midoShop.DocumentService.Repositories.DocRepo;
import jakarta.ws.rs.NotAllowedException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.stereotype.Component;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AiDocService {

    private final String DIRECTORY_PATH="\\Users\\mac\\Desktop\\Backend\\Store";


    private final ChatClient chatClient;

    private final DocRepo docRepo;

    public AiDocService(ChatClient.Builder builder, DocRepo docRepo) {
        this.chatClient = builder.build();
        this.docRepo = docRepo;
    }

    public MyDocumentType ClassifyDocumentByTypeWithAi(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is null or empty.");
        }
        String filename = file.getOriginalFilename();

        if (filename == null || !filename.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        PagePdfDocumentReader pdfFile = new PagePdfDocumentReader(file.getResource());
        List<Document> documents = pdfFile.get();

        String documentText = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n--- Page Break ---\n"));

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

    public PdfResumeOutput ResumePdfDocumentWithAi(Long docId) throws IOException {
        if (docId == null) throw new IllegalArgumentException("Document ID cannot be null");

        Optional<MyDocument> document = docRepo.findById(docId);

        if (document.isEmpty()) throw new FileNotFoundException("Document not found");

        MyDocument doc = document.get();
        if (!doc.getMimeType().endsWith("pdf")) {
            throw new NotAllowedException("This format is not allowed. Only PDF supported.");
        }

        File file = new File(DIRECTORY_PATH + File.separator + doc.getOriginalFilename());
        if (!file.exists()) throw new FileNotFoundException("File not found on disk");

        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(file.getPath());

        List<Document> documents = pdfReader.get();

        String documentText = documents.stream()
                .map(org.springframework.ai.document.Document::getText)
                .collect(Collectors.joining("\n--- Page Break ---\n"));

       return chatClient.prompt()
                .system("You are an assistant that summarizes PDF documents , summarize in max 50 word")
                .user(u -> u.text(documentText))
                .call()
                .entity(PdfResumeOutput.class);



    }






}
