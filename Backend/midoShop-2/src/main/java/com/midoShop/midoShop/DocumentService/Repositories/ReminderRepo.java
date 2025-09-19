package com.midoShop.midoShop.DocumentService.Repositories;

import com.midoShop.midoShop.DocumentService.Models.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderRepo extends JpaRepository<Reminder , Long> {
    List<Reminder> findAllByUserId(String userId);

    Optional<Reminder> findByDocIdAndUserId(Long docId, String userId);
}
