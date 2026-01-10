package com.example.anxiety_senpai.domain.card.repository;

import com.example.anxiety_senpai.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
