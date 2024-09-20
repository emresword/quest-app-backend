package com.example.questApp.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.questApp.entities.concretes.Message;
import com.example.questApp.entities.concretes.User;

public interface MessageDao extends JpaRepository<Message, Long> {
	List<Message> findByReceiverId(Long receiverId);

	List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

	@Query("SELECT m FROM Message m WHERE (m.sender.id = :senderUserId AND m.receiver.id = :receiverUserId) OR (m.sender.id = :receiverUserId AND m.receiver.id = :senderUserId)")
	List<Message> findMessagesBetweenUsers(@Param("senderUserId") Long senderUserId,
			@Param("receiverUserId") Long receiverUserId);
	 @Query("SELECT m FROM Message m WHERE m.sender.id = :userId OR m.receiver.id = :userId")
	    List<Message> findBySenderIdOrReceiverId(@Param("userId") Long userId);

}
