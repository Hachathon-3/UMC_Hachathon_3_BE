package com.example.anxiety_senpai.domain.card.dto;

public record MyCardStatsResponse(
        long totalCards,
        long solvedCards,
        long unsolvedCards,
        long commentedCards
) {}

