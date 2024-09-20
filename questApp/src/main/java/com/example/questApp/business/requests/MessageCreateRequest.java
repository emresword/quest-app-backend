package com.example.questApp.business.requests;

import lombok.Data;

@Data
public class MessageCreateRequest {
    Long senderUserId;  // Gönderen kullanıcı ID'si
    Long receiverUserId;  // Alıcı kullanıcı ID'si
    String text; 
}
