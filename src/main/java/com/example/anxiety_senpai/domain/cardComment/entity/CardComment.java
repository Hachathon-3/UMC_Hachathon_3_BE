package com.example.anxiety_senpai.domain.cardComment.entity;

import com.example.anxiety_senpai.domain.card.entity.Card;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.global.entity.BaseEntity;
import com.example.anxiety_senpai.domain.cardComment.enums.CardCommentStatus;
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
        name = "card_comments",
        indexes = {
                @Index(name = "idx_card_comments_card_id", columnList = "card_id"),
                @Index(name = "idx_card_comments_user_id", columnList = "user_id"),
                @Index(name = "idx_card_comments_parent_comment_id", columnList = "parent_comment_id")
        }
)
public class CardComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    /**
     * 댓글이 달린 게시물
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    /**
     * 댓글 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 부모 댓글 (null이면 루트 댓글)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private CardComment parentComment;

    /**
     * 자식 댓글(대댓글)
     * - orphanRemoval ❌ (soft delete / best 처리 고려)
     */
    @OneToMany(mappedBy = "parentComment")
    @OrderBy("createdAt ASC") // BaseEntity 필드 사용 가능
    @Builder.Default
    private List<CardComment> children = new ArrayList<>();

    @Lob
    @Column(name = "content")
    private String content;

    /**
     * 베스트 댓글 여부
     */
    @Column(name = "is_best")
    @Builder.Default
    private Boolean isBest = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private CardCommentStatus status = CardCommentStatus.ACTIVE;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /* ================= 도메인 메서드 ================= */

    /** 루트 댓글 여부 */
    public boolean isRoot() {
        return parentComment == null;
    }

    /** 베스트 댓글 지정 */
    public void markBest() {
        this.isBest = true;
    }

    /** 베스트 댓글 해제 */
    public void unmarkBest() {
        this.isBest = false;
    }

    /** 댓글 수정 */
    public void updateContent(String content) {
        this.content = content;
    }

    /** 상태 조회 */
    public boolean isActive() {
        return status == CardCommentStatus.ACTIVE;
    }

    /** 숨김 처리 */
    public void hide() {
        this.status = CardCommentStatus.HIDDEN;
    }

    /** 복원 처리 */
    public void restore() {
        this.status = CardCommentStatus.ACTIVE;
        this.deletedAt = null;
    }

    /** 삭제 처리 */
    public void softDelete() {
        this.status = CardCommentStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
    }
}