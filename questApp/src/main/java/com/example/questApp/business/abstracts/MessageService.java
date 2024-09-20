package com.example.questApp.business.abstracts;

import java.util.List;
import java.util.Optional;

import com.example.questApp.business.requests.MessageCreateRequest;
import com.example.questApp.business.responses.MessageResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.entities.concretes.Message;
import com.example.questApp.entities.concretes.User;

public interface MessageService {
	DataResult<List<MessageResponse>> getAll(Long senderUserId,Long recieverUserId);
	DataResult<MessageResponse> getById(Long id);
	DataResult<Message> add(MessageCreateRequest messageCreateRequest);
	DataResult<List<MessageResponse>> getAllMessagesSenderId(Long userId);

}
