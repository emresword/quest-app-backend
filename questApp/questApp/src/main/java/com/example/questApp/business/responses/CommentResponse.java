package com.example.questApp.business.responses;

import com.example.questApp.entities.concretes.Comment;
import lombok.Data;

@Data
public class CommentResponse {
    Long id;
    Long userId;
    String userName;
    Long postId;
    String text;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.userName = comment.getUser().getUserName();
        this.postId = comment.getPost().getId();
        this.text = comment.getText();
    }
}
