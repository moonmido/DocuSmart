package com.midoShop.midoShop.DocumentService.Services;

import com.midoShop.midoShop.DocumentService.Inputs.ReminderRequestInput;
import com.midoShop.midoShop.DocumentService.Models.MyDocument;
import com.midoShop.midoShop.DocumentService.Models.Reminder;
import com.midoShop.midoShop.DocumentService.Repositories.DocRepo;
import com.midoShop.midoShop.DocumentService.Repositories.ReminderRepo;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ReminderService {

    private final DocRepo docRepo;

    private final ReminderRepo reminderRepo;

    public ReminderService(DocRepo docRepo, ReminderRepo reminderRepo) {
        this.docRepo = docRepo;
        this.reminderRepo = reminderRepo;
    }

    public Reminder createReminder(ReminderRequestInput reminderRequestInput) throws FileNotFoundException {
    if(reminderRequestInput.docId()==null || reminderRequestInput.userId()==null) throw new IllegalArgumentException();

    if(!isDocumentExistById(reminderRequestInput.docId(),reminderRequestInput.userId()))  throw new FileNotFoundException();

    Reminder reminder = new Reminder();
    reminder.setDocId(reminderRequestInput.docId());
    reminder.setUserId(reminderRequestInput.userId());
    reminder.setMessage(reminderRequestInput.message());
    reminder.setReminderDate(reminderRequestInput.reminderDate());
    reminder.setCreatedAt(LocalDateTime.now());
    reminder.setActive(reminderRequestInput.status());

    return reminderRepo.save(reminder);
    }


    public boolean isDocumentExistById(Long docId , String userId){
        Optional<MyDocument> byIdAndUserId = docRepo.findByIdAndUserId(docId, userId);
        return byIdAndUserId.isPresent();
    }

    public List<MyDocument> checkExpiringDocuments(String userId) throws FileNotFoundException {
        if(userId==null) throw new IllegalArgumentException();

        try {

            List<MyDocument> allByUserId = docRepo.findAllByUserId(userId);

          return allByUserId.stream()
                    .filter(a -> a.getExpiryDate().isAfter(LocalDate.now())).toList();

        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }

    public List<Reminder> getUserReminders(String userId){
        if(userId==null) throw new IllegalArgumentException();

        try {

            return reminderRepo.findAllByUserId(userId);

        }catch (Exception e){
            throw new NullPointerException();
        }
    }

    public Reminder GetReminderByDocId(Long docId , String userId){
        if(userId==null ||docId==null) throw new IllegalArgumentException();

        Optional<Reminder> byDocIdAndUserId = reminderRepo.findByDocIdAndUserId(docId, userId);
        return byDocIdAndUserId.orElseThrow(NoSuchElementException::new);
    }

    public boolean updateReminder(ReminderRequestInput reminderRequestInput){
        if(reminderRequestInput.userId()==null ||reminderRequestInput.docId()==null) throw new IllegalArgumentException();

        Optional<Reminder> byDocIdAndUserId = reminderRepo.findByDocIdAndUserId(reminderRequestInput.docId(), reminderRequestInput.userId());
        if(byDocIdAndUserId.isEmpty()) return false;
        Reminder reminder = byDocIdAndUserId.get();
        reminder.setActive(reminderRequestInput.status());
        reminder.setReminderDate(reminderRequestInput.reminderDate());
        reminder.setMessage(reminderRequestInput.message());
        reminder.setUserId(reminderRequestInput.userId());
        reminder.setDocId(reminderRequestInput.docId());
        reminder.setCreatedAt(reminder.getCreatedAt());

        reminderRepo.save(reminder);
        return true;
    }

}
