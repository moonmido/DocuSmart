package com.midoShop.midoShop.DocumentService.Inputs;

import java.time.LocalDateTime;

public record ReminderRequestInput(Long docId , String userId , String message , LocalDateTime reminderDate , Boolean status) {
}
