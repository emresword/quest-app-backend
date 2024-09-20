package com.example.questApp.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.MessageService;
import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.business.requests.MessageCreateRequest;
import com.example.questApp.business.responses.MessageResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.ErrorDataResult;
import com.example.questApp.core.utilities.results.SuccessDataResult;
import com.example.questApp.dataAccess.abstracts.MessageDao;
import com.example.questApp.entities.concretes.Message;
import com.example.questApp.entities.concretes.User;

@Service
public class MessageManager implements MessageService {

    private MessageDao messageDao;
    private UserService userService;

    @Autowired
    public MessageManager(MessageDao messageDao, UserService userService) {
        super();
        this.messageDao = messageDao;
        this.userService = userService;
    }

 
    @Override
    public DataResult<List<MessageResponse>> getAll(Long senderUserId, Long receiverUserId) {
        List<Message> messages;

        if (senderUserId != null && receiverUserId != null) {
          
            messages = messageDao.findMessagesBetweenUsers(senderUserId, receiverUserId);
        } else {
            
            messages = messageDao.findAll();
        }

        List<MessageResponse> messageResponses = messages.stream()
                .map(message -> new MessageResponse(message))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(messageResponses, "Messages retrieved successfully.");
    }


    @Override
    public DataResult<Message> add(MessageCreateRequest messageCreateRequest) {
        DataResult<User> senderResult = userService.getById(messageCreateRequest.getSenderUserId());
        DataResult<User> receiverResult = userService.getById(messageCreateRequest.getReceiverUserId());

        if (senderResult.isSuccess() && receiverResult.isSuccess()) {
            Message message = new Message();
            message.setText(messageCreateRequest.getText());
            message.setSender(senderResult.getData());
            message.setReceiver(receiverResult.getData());
            Message savedMessage = messageDao.save(message);
            return new SuccessDataResult<>(savedMessage, "Message added successfully.");
        } else {
            return new ErrorDataResult<>("Sender or receiver not found.");
        }
    }

    @Override
    public DataResult<MessageResponse> getById(Long id) {
        Message message = messageDao.findById(id).orElse(null);

        if (message != null) {
            return new SuccessDataResult<>(new MessageResponse(message), "Message retrieved successfully.");
        } else {
            return new ErrorDataResult<>("Message not found.");
        }
    }
    
    @Override
    public DataResult<List<MessageResponse>> getAllMessagesSenderId(Long userId) {
        List<Message> messages = messageDao.findBySenderIdOrReceiverId(userId);
        if (messages != null && !messages.isEmpty()) {
            List<MessageResponse> messageResponses = messages.stream()
                    .map(message -> new MessageResponse(message))
                    .collect(Collectors.toList());
            return new SuccessDataResult<>(messageResponses, "Conversation messages retrieved successfully.");
        } else {
            return new ErrorDataResult<>("No conversation messages found.");
        }
    }




}
