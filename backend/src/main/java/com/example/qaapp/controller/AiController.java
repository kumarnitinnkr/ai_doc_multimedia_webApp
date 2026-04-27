package com.example.qaapp.controller;

import com.example.qaapp.dto.*;
import com.example.qaapp.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/transcribe/{fileId}")
    public Map<String, String> transcribe(
            @PathVariable Long fileId) {

        String transcript =
                aiService.transcribe(fileId);

        return Map.of(
                "transcript", transcript
        );
    }

    @PostMapping("/ask")
    public ChatResponse ask(
            @RequestBody ChatRequest req) {

        return new ChatResponse(
                aiService.ask(
                        req.getFileId(),
                        req.getQuestion()
                )
        );
    }

    @GetMapping("/summary/{fileId}")
    public SummaryResponse summary(
            @PathVariable Long fileId) {

        return new SummaryResponse(
                aiService.summary(fileId)
        );
    }

    @GetMapping("/timestamp/{fileId}")
    public TimestampResponse timestamp(
            @PathVariable Long fileId,
            @RequestParam String topic) {

        return new TimestampResponse(
                topic,
                aiService.timestamp(fileId, topic)
        );
    }
}