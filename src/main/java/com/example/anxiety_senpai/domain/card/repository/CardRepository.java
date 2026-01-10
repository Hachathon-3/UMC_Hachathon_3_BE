package com.example.anxiety_senpai.domain.card.repository;

import com.example.anxiety_senpai.domain.card.entity.Card;
import com.example.anxiety_senpai.domain.card.enums.SolveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("select count(c) from Card c where c.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("select count(c) from Card c where c.user.id = :userId and c.solveStatus = :status")
    long countByUserIdAndSolveStatus(@Param("userId") Long userId, @Param("status") SolveStatus status);
}
