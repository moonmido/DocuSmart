package com.midoShop.midoShop.DocumentService.Inputs;

import com.midoShop.midoShop.DocumentService.DTOs.MyDocumentType;

import java.time.LocalDate;

public record MyDocumentInputInfo(String DocName , MyDocumentType myDocumentType , LocalDate issueDate , LocalDate ExpiryDate , String tags) {
}
