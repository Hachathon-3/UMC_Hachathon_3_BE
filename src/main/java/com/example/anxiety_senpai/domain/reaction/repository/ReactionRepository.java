package com.example.anxiety_senpai.domain.reaction.repository;

import com.example.anxiety_senpai.domain.reaction.entity.Reaction;
import com.example.anxiety_senpai.domain.reaction.enums.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    interface ReactionSummaryView {
        ReactionType type();
        Long count();
    }

    @Query("""
        select r.type as type, count(r) as count
        from Reaction r
        where r.card.id = :cardId
          and r.card is not null
        group by r.type
    """)
    List<ReactionSummaryView> countSummaryByCardId(@Param("cardId") Long cardId);

    @Query("""
        select r.type
        from Reaction r
        where r.card.id = :cardId
          and r.card is not null
          and r.user.id = :userId
    """)
    List<ReactionType> findMyReactionsOnCard(@Param("cardId") Long cardId,
                                             @Param("userId") Long userId);
}

