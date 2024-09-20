package com.example.questApp.business.responses;

import com.example.questApp.entities.concretes.Message;
import lombok.Data;

@Data
public class MessageResponse {

    private Long id;
    private Long senderUserId;
    private Long receiverUserId;
    private String text;

    public MessageResponse(Message message) {
        this.id = message.getId();
        this.senderUserId =  message.getSender().getId() ;
        this.receiverUserId =  message.getReceiver().getId();
        this.text = message.getText();
    }
}
