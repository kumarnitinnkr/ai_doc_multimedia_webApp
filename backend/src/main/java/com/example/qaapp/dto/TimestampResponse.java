package com.example.qaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TimestampResponse {
    private String topic;
    private String timestamp;
}