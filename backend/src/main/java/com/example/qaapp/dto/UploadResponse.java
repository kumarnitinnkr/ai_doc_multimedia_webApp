package com.example.qaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UploadResponse {

    private Long id;
    private String fileName;
    private String type;
    private String message;
}