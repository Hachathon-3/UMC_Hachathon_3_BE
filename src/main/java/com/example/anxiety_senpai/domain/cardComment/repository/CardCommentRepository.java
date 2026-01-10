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
}
