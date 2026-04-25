package com.example.qaapp.controller;

import com.example.qaapp.entity.MediaFile;
import com.example.qaapp.repository.MediaFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/media")
public class StreamController {

    private final MediaFileRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<FileSystemResource> stream(
            @PathVariable Long id) {

        MediaFile file =
            repository.findById(id).orElseThrow();

        return ResponseEntity.ok(
            new FileSystemResource(file.getFilePath())
        );
    }
}