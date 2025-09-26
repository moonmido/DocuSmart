package com.midoShop.midoShop.DocumentService.Models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.midoShop.midoShop.DocumentService.DTOs.MyDocumentType;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(indexName = "documents")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyDocES {

    private Long id;

    private String userId;
    private String name;
    private String originalFilename;
    private MyDocumentType type;
    private String filePath;
    private Long fileSize;
    private String mimeType;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private LocalDateTime uploadedAt;
    private String tags;
    private LocalDate CreatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public MyDocumentType getType() {
        return type;
    }

    public void setType(MyDocumentType type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public LocalDate getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        CreatedAt = createdAt;
    }
}
