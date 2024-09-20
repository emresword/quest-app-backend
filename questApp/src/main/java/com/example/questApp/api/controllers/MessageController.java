package com.example.questApp.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questApp.business.abstracts.MessageService;
import com.example.questApp.business.requests.MessageCreateRequest;
import com.example.questApp.business.responses.MessageResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.ErrorDataResult;
import com.example.questApp.core.utilities.results.SuccessDataResult;
import com.example.questApp.entities.concretes.Message;
import com.example.questApp.entities.concretes.User;

@RestController
@RequestMapping("/messages")
@CrossOrigin
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<?> getMessages(@RequestParam("senderUserId") Long senderUserId, @RequestParam("receiverUserId") Long receiverUserId) {
        try {
            DataResult<List<MessageResponse>> result = messageService.getAll(senderUserId, receiverUserId);
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDataResult<>("Failed to fetch messages"));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<DataResult<MessageResponse>> add(@RequestBody MessageCreateRequest messageCreateRequest) {
        DataResult<Message> result = messageService.add(messageCreateRequest);

        if (result.isSuccess()) {
            return ResponseEntity.ok(new SuccessDataResult<>(new MessageResponse(result.getData()), "Message added successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDataResult<>(result.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<MessageResponse>> getById(@PathVariable Long id) {
        DataResult<MessageResponse> result = messageService.getById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }
    @GetMapping("/conversations")
    public ResponseEntity<?> getAllMessagesSenderId(@RequestParam("userId") Long userId) {
        DataResult<List<MessageResponse>> result = messageService.getAllMessagesSenderId(userId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

}
