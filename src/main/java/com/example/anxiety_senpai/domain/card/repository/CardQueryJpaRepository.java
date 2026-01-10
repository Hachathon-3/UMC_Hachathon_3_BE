package com.example.anxiety_senpai.domain.card.repository;

import com.example.anxiety_senpai.domain.card.dto.CardListItemResponse;
import com.example.anxiety_senpai.domain.card.entity.Card;
import com.example.anxiety_senpai.domain.card.enums.SolveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface CardQueryJpaRepository extends JpaRepository<Card, Long> {

    @Query(
            value = """
                select distinct new com.example.anxiety_senpai.domain.card.dto.CardListItemResponse(
                    c.id,
                    c.title,
                    substring(c.content, 1, 60),
                    c.solveStatus,
                    c.allowComment,
                    new com.example.anxiety_senpai.domain.card.dto.AuthorResponse(u.id, u.name),
                    c.createdAt,
                    c.updatedAt
                )
                from Card c
                join c.user u
                left join c.cardTags ct
                left join ct.tag t
                where c.status = com.example.anxiety_senpai.domain.card.enums.CardStatus.ACTIVE
                  and (:userId is null or c.user.id = :userId)
                  and (:solveStatus is null or c.solveStatus = :solveStatus)
                  and (
                        :keyword is null
                        or lower(c.title) like lower(concat('%', :keyword, '%'))
                        or lower(c.content) like lower(concat('%', :keyword, '%'))
                  )
                  and (:tagId is null or t.id = :tagId)
                """,
            countQuery = """
                select count(distinct c.id)
                from Card c
                left join c.cardTags ct
                left join ct.tag t
                where c.status = com.example.anxiety_senpai.domain.card.enums.CardStatus.ACTIVE
                  and (:userId is null or c.user.id = :userId)
                  and (:solveStatus is null or c.solveStatus = :solveStatus)
                  and (
                        :keyword is null
                        or lower(c.title) like lower(concat('%', :keyword, '%'))
                        or lower(c.content) like lower(concat('%', :keyword, '%'))
                  )
                  and (:tagId is null or t.id = :tagId)
                """
    )
    Page<CardListItemResponse> searchCards(
            @Param("userId") Long userId,
            @Param("tagId") Long tagId,
            @Param("solveStatus") SolveStatus solveStatus,
            @Param("keyword") String keyword,
            Pageable pageable
    );

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
        left join com.example.anxiety_senpai.domain.reaction.entity.Reaction r on r.card.id = c.id
        where c.status = com.example.anxiety_senpai.domain.card.enums.CardStatus.ACTIVE
          and (:tagId is null or ct.tag.id = :tagId)
          and (:solveStatus is null or c.solveStatus = :solveStatus)
          and (:keyword is null or lower(c.title) like lower(concat('%', :keyword, '%')) or lower(c.content) like lower(concat('%', :keyword, '%')))
        group by c.id, c.title, c.content, c.solveStatus, c.allowComment, c.user.id, c.user.name, c.createdAt, c.updatedAt
        order by count(r) desc, c.createdAt desc
    """)
    Page<CardListItemResponse> searchCardsOrderByReactionDesc(
            @Param("tagId") Long tagId,
            @Param("solveStatus") SolveStatus solveStatus,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
