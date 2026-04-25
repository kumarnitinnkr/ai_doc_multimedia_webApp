package com.example.qaapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {
    private Long fileId;
    private String question;
}
