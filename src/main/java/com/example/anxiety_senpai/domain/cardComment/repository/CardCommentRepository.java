package com.example.anxiety_senpai.domain.cardComment.repository;

import com.example.anxiety_senpai.domain.cardComment.entity.CardComment;
import com.example.anxiety_senpai.domain.cardComment.enums.CardCommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardCommentRepository extends JpaRepository<CardComment, Long> {
    Page<CardComment> findByCardIdAndStatus(Long cardId, CardCommentStatus status, Pageable pageable);
}
