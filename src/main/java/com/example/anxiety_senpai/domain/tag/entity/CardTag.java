package com.example.anxiety_senpai.domain.tag.entity;

import com.example.anxiety_senpai.domain.card.entity.Card;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(
        name = "card_tag",
        indexes = {
                @Index(name = "idx_card_tag_card_id", columnList = "card_id"),
                @Index(name = "idx_card_tag_tag_id", columnList = "tag_id")
        }
)
@IdClass(CardTagId.class)
public class CardTag {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    // 선택: 연결 생성 시각이 필요하면 (있으면 운영/정렬/통계에 도움)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}