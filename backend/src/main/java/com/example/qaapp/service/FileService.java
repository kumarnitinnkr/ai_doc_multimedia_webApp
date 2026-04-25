package com.example.qaapp.service;

import com.example.qaapp.entity.MediaFile;
import com.example.qaapp.repository.MediaFileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MediaFileRepository repository;

    public MediaFile upload(MultipartFile file) throws Exception {

    File dir = new File("C:/uploads");
    if (!dir.exists()) dir.mkdirs();

    String uniqueName =
            UUID.randomUUID() + "_" + file.getOriginalFilename();

    File destination = new File(dir, uniqueName);

    file.transferTo(destination);

    String extracted = "";

    if (file.getContentType() != null &&
            file.getContentType().contains("pdf")) {

        PDDocument document =
                Loader.loadPDF(destination);

        PDFTextStripper stripper =
                new PDFTextStripper();

        extracted = stripper.getText(document);
        document.close();
    }

    MediaFile saved = MediaFile.builder()
            .fileName(uniqueName)
            .originalName(file.getOriginalFilename())
            .fileType(resolveType(file.getContentType()))
            .contentType(file.getContentType())
            .fileSize(file.getSize())
            .filePath(destination.getAbsolutePath())
            .extractedText(extracted)
            .uploadedAt(LocalDateTime.now())
            .build();

    return repository.save(saved);
}

    private String resolveType(String type) {

        if (type == null) return "UNKNOWN";

        if (type.contains("pdf")) return "PDF";
        if (type.contains("audio")) return "AUDIO";
        if (type.contains("video")) return "VIDEO";

        return "UNKNOWN";
    }
}