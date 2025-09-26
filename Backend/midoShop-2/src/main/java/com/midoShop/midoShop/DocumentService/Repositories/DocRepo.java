package com.midoShop.midoShop.DocumentService.Repositories;

import com.midoShop.midoShop.DocumentService.DTOs.MyDocumentType;
import com.midoShop.midoShop.DocumentService.Models.MyDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocRepo extends JpaRepository<MyDocument,Long> {

    List<MyDocument> findAllByUserId(String userId);

    List<MyDocument> findByNameAndUserId(String name , String userId);

    Optional<MyDocument> findByIdAndUserId(Long docId, String userId);

    long countByUserId(String userId);

    long countByType(MyDocumentType type);
}
