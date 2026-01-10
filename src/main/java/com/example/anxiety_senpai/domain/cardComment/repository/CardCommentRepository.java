package com.example.anxiety_senpai.domain.cardComment.repository;

import com.example.anxiety_senpai.domain.cardComment.entity.CardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardCommentRepository extends JpaRepository<CardComment, Long> {
}

