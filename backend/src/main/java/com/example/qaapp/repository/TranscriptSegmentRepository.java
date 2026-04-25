package com.example.qaapp.repository;

import com.example.qaapp.entity.TranscriptSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranscriptSegmentRepository
        extends JpaRepository<TranscriptSegment, Long> {

    List<TranscriptSegment> findByFileId(Long fileId);
}