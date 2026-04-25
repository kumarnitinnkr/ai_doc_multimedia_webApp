package com.example.qaapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranscriptSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fileId;

    private Integer startSecond;

    private Integer endSecond;

    @Column(columnDefinition = "LONGTEXT")
    private String text;
}