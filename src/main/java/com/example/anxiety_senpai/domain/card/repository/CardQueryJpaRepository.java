package com.example.anxiety_senpai.domain.card.repository;

import com.example.anxiety_senpai.domain.card.dto.CardListItemResponse;
import com.example.anxiety_senpai.domain.card.enums.CardStatus;
import com.example.anxiety_senpai.domain.card.enums.SolveStatus;
import com.example.anxiety_senpai.domain.card.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardQueryJpaRepository extends JpaRepository<Card, Long> {

    @Query("""
        select new com.example.anxiety_senpai.domain.card.dto.CardListItemResponse(
            c.id,
            c.title,
            substring(c.content, 1, 100),
            c.solveStatus,
            c.allowComment,
            new com.example.anxiety_senpai.domain.card.dto.CardListItemResponse$AuthorResponse(c.user.id, c.user.name),
            c.createdAt,
            c.updatedAt
        )
        from Card c
        left join c.cardTags ct
        where c.status = com.example.anxiety_senpai.domain.card.enums.CardStatus.ACTIVE
          and (:tagId is null or ct.tag.id = :tagId)
          and (:solveStatus is null or c.solveStatus = :solveStatus)
          and (:keyword is null or lower(c.title) like lower(concat('%', :keyword, '%')) or lower(c.content) like lower(concat('%', :keyword, '%')))
        group by c.id, c.title, c.content, c.solveStatus, c.allowComment, c.user.id, c.user.name, c.createdAt, c.updatedAt
    """)
    Page<CardListItemResponse> searchCards(
            @Param("tagId") Long tagId,
            @Param("solveStatus") SolveStatus solveStatus,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
