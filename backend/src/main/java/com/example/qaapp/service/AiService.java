package com.example.qaapp.service;

import com.example.qaapp.entity.MediaFile;
import com.example.qaapp.entity.TranscriptSegment;
import com.example.qaapp.repository.MediaFileRepository;
import com.example.qaapp.repository.TranscriptSegmentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {

    private final MediaFileRepository repository;
    private final TranscriptSegmentRepository segmentRepository;
    @Value("${openai.api.key}")
    private String apiKey;

    public String transcribe(Long fileId) {

    MediaFile file = repository.findById(fileId).orElseThrow();

    String sample =
            "0|30|Introduction\n" +
            "31|90|Java Basics\n" +
            "91|180|Spring Boot Setup\n" +
            "181|260|MySQL Integration";

    String[] lines = sample.split("\n");

    for (String line : lines) {

        String[] p = line.split("\\|");

        segmentRepository.save(
            TranscriptSegment.builder()
                .fileId(fileId)
                .startSecond(Integer.parseInt(p[0]))
                .endSecond(Integer.parseInt(p[1]))
                .text(p[2])
                .build()
        );
    }

    file.setExtractedText(sample);
    repository.save(file);

    return "Transcript Stored";
}

    public String ask(Long fileId, String question) {

        MediaFile file = repository.findById(fileId).orElseThrow();

        String prompt =
                "Use below content and answer accurately.\n\n"
                + file.getExtractedText()
                + "\n\nQuestion: " + question;

        return chat(prompt);
    }

    public String summary(Long fileId) {

        MediaFile file = repository.findById(fileId).orElseThrow();

        return chat("Summarize this content:\n\n" +
                file.getExtractedText());
    }

    public String timestamp(Long fileId, String topic) {

    List<TranscriptSegment> list =
            segmentRepository.findByFileId(fileId);

    for (TranscriptSegment t : list) {

        if (t.getText().toLowerCase()
            .contains(topic.toLowerCase())) {

            int s = t.getStartSecond();

            return String.format("%02d:%02d",
                    s / 60, s % 60);
        }
    }

    return "00:00";
}

    private String chat(String prompt) {

        WebClient client = WebClient.builder()
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();

        Map response = client.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                        "model", "gpt-4o-mini",
                        "messages", List.of(
                                Map.of(
                                        "role", "user",
                                        "content", prompt
                                )
                        )
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List<Map> choices =
                (List<Map>) response.get("choices");

        Map msg =
                (Map) choices.get(0).get("message");

        return msg.get("content").toString();
    }
}
