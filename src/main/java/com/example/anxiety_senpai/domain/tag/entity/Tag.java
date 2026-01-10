package com.example.anxiety_senpai.domain.tag.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(
        name = "tags",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_tags_name", columnNames = "name")
        },
        indexes = {
                @Index(name = "idx_tags_name", columnList = "name")
        }
)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    // 선택: 태그 생성 시각이 필요하면
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CardTag> cardTags = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}