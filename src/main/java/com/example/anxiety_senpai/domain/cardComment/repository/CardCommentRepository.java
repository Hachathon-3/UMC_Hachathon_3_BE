package com.example.anxiety_senpai.domain.cardComment.repository;

import com.example.anxiety_senpai.domain.cardComment.entity.CardComment;
import com.example.anxiety_senpai.domain.cardComment.enums.CardCommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardCommentRepository extends JpaRepository<CardComment, Long> {
    Page<CardComment> findByCardIdAndStatus(Long cardId, CardCommentStatus status, Pageable pageable);

    @Query("select count(distinct cc.card.id) from CardComment cc where cc.user.id = :userId")
    long countDistinctCardByUserId(@Param("userId") Long userId);

    @Query("""
        select new com.example.anxiety_senpai.domain.mypage.dto.CommentedCardResponse(
            cc.card.id,
            c.title,
            substring(c.content, 1, 100),
            c.solveStatus,
            c.allowComment,
            max(cc.createdAt),
            c.createdAt,
            c.updatedAt
        )
        from CardComment cc
        join cc.card c
        where cc.user.id = :userId
          and c.status = com.example.anxiety_senpai.domain.card.enums.CardStatus.ACTIVE
        group by cc.card.id, c.title, c.content, c.solveStatus, c.allowComment, c.createdAt, c.updatedAt
    """)
    Page<com.example.anxiety_senpai.domain.mypage.dto.CommentedCardResponse> findCommentedCards(@Param("userId") Long userId, Pageable pageable);
}
