package com.midoShop.midoShop.DocumentService.Outputs;

import com.midoShop.midoShop.DocumentService.DTOs.MyDocumentType;

import java.time.LocalDate;

public record UserDocumentOutput(String name , MyDocumentType myDocumentType , String tags , LocalDate CreatedAt) {
}
