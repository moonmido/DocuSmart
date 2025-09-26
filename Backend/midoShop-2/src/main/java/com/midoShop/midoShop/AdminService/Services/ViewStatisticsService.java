package com.midoShop.midoShop.AdminService.Services;


import com.midoShop.midoShop.AdminService.Outputs.DocumentTypeUserStatistics;
import com.midoShop.midoShop.DocumentService.DTOs.MyDocumentType;
import com.midoShop.midoShop.DocumentService.Models.MyDocument;
import com.midoShop.midoShop.DocumentService.Repositories.DocRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ViewStatisticsService {

    private final DocRepo docRepo;


    public ViewStatisticsService(DocRepo docRepo) {
        this.docRepo = docRepo;
    }

    public Long GetAllDocumentsSum(){
       return docRepo.count();
    }

    public double GetAllStorageUsedSum(){
        List<MyDocument> all = docRepo.findAll();
        double s = 0;
        for(MyDocument myDocument : all){
            s+=myDocument.getFileSize();
        }
        return s;
    }

    public DocumentTypeUserStatistics GetDocTypeStatistics(){

        long CERTIFICATE = docRepo.countByType(MyDocumentType.CERTIFICATE);
        long IDENTITY = docRepo.countByType(MyDocumentType.IDENTITY);
        long CONTRACT = docRepo.countByType(MyDocumentType.CONTRACT);
        long INVOICE = docRepo.countByType(MyDocumentType.INVOICE);
        long OTHER = docRepo.countByType(MyDocumentType.OTHER);

        Long allDocsSum = GetAllDocumentsSum();

        DocumentTypeUserStatistics documentTypeUserStatistics = new DocumentTypeUserStatistics();

        documentTypeUserStatistics.setCERTIFICATE((double) (CERTIFICATE * 100) /allDocsSum);
        documentTypeUserStatistics.setIDENTITY((double) (IDENTITY * 100) /allDocsSum);
        documentTypeUserStatistics.setCONTRACT((double) (CONTRACT * 100) /allDocsSum);
        documentTypeUserStatistics.setINVOICE((double) (INVOICE * 100) /allDocsSum);
        documentTypeUserStatistics.setOTHER((double) (OTHER * 100) /allDocsSum);

        return documentTypeUserStatistics;
    }





}
