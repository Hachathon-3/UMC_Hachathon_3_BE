package com.example.anxiety_senpai.domain.card.entity;

import com.example.anxiety_senpai.domain.card.enums.CardStatus;
import com.example.anxiety_senpai.domain.card.enums.SolveStatus;
import com.example.anxiety_senpai.domain.tag.entity.CardTag;
import com.example.anxiety_senpai.domain.tag.entity.Tag;
import com.example.anxiety_senpai.domain.user.entity.User;
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
        name = "cards",
        indexes = {
                @Index(name = "idx_cards_user_id", columnList = "user_id"),
                @Index(name = "idx_cards_created_at", columnList = "created_at"),
                @Index(name = "idx_cards_solve_status", columnList = "solve_status"),
                @Index(name = "idx_cards_status", columnList = "status")
        }
)
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "content", length = 4000)
    private String content;

    /**
     * 게시물 상태 (ACTIVE/HIDDEN/DELETED)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private CardStatus status = CardStatus.ACTIVE;

    /**
     * 해결 상태 (SOLVED/UNSOLVED)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "solve_status", nullable = false, length = 20)
    @Builder.Default
    private SolveStatus solveStatus = SolveStatus.UNSOLVED;

    /**
     * 댓글 허용 여부
     */
    @Column(name = "allow_comment", nullable = false)
    @Builder.Default
    private Boolean allowComment = true;

    /**
     * 타임스탬프
     * - createdAt: 생성 시각
     * - updatedAt: 수정 시각 (업데이트마다 갱신)
     * - deletedAt: soft delete 시각
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

//    @Column(name = "deleted_at")
//    private LocalDateTime deletedAt;

    /**
     * 연관관계(선택)
     * - 목록/상세 조회 시 N+1 방지 위해 보통 댓글/리액션은 별도 쿼리로 가져오는 편이라
     *   엔티티에 굳이 안 넣어도 된다.
     */

//    // 댓글 (CardComment 엔티티가 card 필드를 가지고 있어야 함)
//    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Builder.Default
//    private List<CardComment> comments = new ArrayList<>();
//
//    // 리액션 (Reaction 엔티티가 card 필드를 가지고 있어야 함)
//    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Builder.Default
//    private List<Reaction> reactions = new ArrayList<>();
//
//    // 태그 (조인 테이블 card_tag)
//    @ManyToMany
//    @JoinTable(
//            name = "card_tag",
//            joinColumns = @JoinColumn(name = "card_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id")
//    )
//    @Builder.Default
//    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CardTag> cardTags = new ArrayList<>();

    public void addTag(Tag tag) {
        // 중복 방지
        boolean exists = cardTags.stream().anyMatch(ct -> ct.getTag().getId().equals(tag.getId()));
        if (!exists) {
            cardTags.add(CardTag.builder().card(this).tag(tag).build());
        }
    }

    public void clearTags() {
        cardTags.clear(); // orphanRemoval=true 이면 연결 row 삭제
    }
    /* ---------- JPA 콜백 ---------- */

    @PrePersist
    private void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;

        // Builder로 null 들어온 경우 안전장치
//        if (this.status == null) this.status = CardStatus.ACTIVE;
        if (this.solveStatus == null) this.solveStatus = SolveStatus.UNSOLVED;
        if (this.allowComment == null) this.allowComment = true;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /* ---------- 도메인 메서드(추천) ---------- */

    public void update(String title, String content, Boolean allowComment) {
        if (title != null) this.title = title;
        if (content != null) this.content = content;
        if (allowComment != null) this.allowComment = allowComment;
    }

    public void markSolved() {
        this.solveStatus = SolveStatus.SOLVED;
    }

    public void markUnsolved() {
        this.solveStatus = SolveStatus.UNSOLVED;
    }

//    public void hide() {
//        this.status = CardStatus.HIDDEN;
//    }
//
//    public void restore() {
//        this.status = CardStatus.ACTIVE;
//        this.deletedAt = null;
//    }
//
//    public void softDelete() {
//        this.status = CardStatus.DELETED;
//        this.deletedAt = LocalDateTime.now();
//    }
}