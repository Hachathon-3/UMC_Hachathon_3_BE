package com.example.anxiety_senpai.domain.card.repository;

import com.example.anxiety_senpai.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardDetailQueryRepository extends JpaRepository<Card, Long> {

    @Query("""
        select distinct c
        from Card c
        join fetch c.user u
        left join fetch c.cardTags ct
        left join fetch ct.tag t
        where c.id = :cardId
    """)
    Optional<Card> findCardDetail(@Param("cardId") Long cardId);
}

