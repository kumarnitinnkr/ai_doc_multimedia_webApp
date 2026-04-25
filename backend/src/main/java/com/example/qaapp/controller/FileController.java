package com.example.qaapp.controller;

import com.example.qaapp.dto.UploadResponse;
import com.example.qaapp.entity.MediaFile;
import com.example.qaapp.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public UploadResponse upload(
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        MediaFile saved = fileService.upload(file);

        return new UploadResponse(
                saved.getId(),
                saved.getOriginalName(),
                saved.getFileType(),
                "Uploaded Successfully"
        );
    }
}