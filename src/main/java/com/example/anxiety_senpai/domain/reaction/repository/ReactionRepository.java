package com.example.anxiety_senpai.domain.reaction.repository;

import com.example.anxiety_senpai.domain.reaction.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    @Query("""
        select count(r)
        from Reaction r
        where r.card.id = :cardId
          and r.card is not null
    """)
    Long countByCardId(@Param("cardId") Long cardId);

    @Query("""
        select count(r)
        from Reaction r
        where r.cardComment.id = :commentId
          and r.cardComment is not null
    """)
    Long countByCommentId(@Param("commentId") Long commentId);

    @Query("""
        select r
        from Reaction r
        where r.card.id = :cardId
          and r.card is not null
          and r.user.id = :userId
    """)
    List<Reaction> findByCardIdAndUserId(@Param("cardId") Long cardId, @Param("userId") Long userId);

    @Query("""
        select r
        from Reaction r
        where r.cardComment.id = :commentId
          and r.cardComment is not null
          and r.user.id = :userId
    """)
    List<Reaction> findByCommentIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);
}
