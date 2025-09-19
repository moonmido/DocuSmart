package com.midoShop.midoShop.StorageService.Services;

import com.midoShop.midoShop.DocumentService.Models.MyDocument;
import com.midoShop.midoShop.DocumentService.Repositories.DocRepo;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class DocStorageService {


    private final DocRepo docRepo;


    public DocStorageService(DocRepo docRepo) {
        this.docRepo = docRepo;
    }

    public Double calculateSumOfFilesSizesOfUser(String userId) throws IOException {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        try {
            List<MyDocument> allByUserId = docRepo.findAllByUserId(userId);


            return allByUserId.stream()
                    .mapToDouble(MyDocument::getFileSize)
                    .sum();

        } catch (Exception e) {
            throw new FileNotFoundException("Error while calculating sizes for user " + userId);
        }
    }



}
