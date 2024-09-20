package com.example.questApp.business.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FlirtIdRequest {
    private Long userId;
    private Long flirtId;

    // Getters and Setters
}
