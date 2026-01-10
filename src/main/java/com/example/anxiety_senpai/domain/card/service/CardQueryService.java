package com.example.anxiety_senpai.domain.card.service;

import com.example.anxiety_senpai.domain.card.dto.CardListItemResponse;
import com.example.anxiety_senpai.domain.card.enums.SolveStatus;
import com.example.anxiety_senpai.domain.card.repository.CardQueryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardQueryService {

    private final CardQueryJpaRepository cardQueryJpaRepository;

    @Transactional(readOnly = true)
    public Page<CardListItemResponse> getCards(
            int page,
            int size,
            String sort,
            String keyword,
            Long tagId,
            SolveStatus solveStatus
    ) {
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.max(size, 1),
                resolveSort(sort)
        );

        // keyword는 공백이면 null로 처리
        String normalizedKeyword = (keyword == null || keyword.trim().isEmpty())
                ? null
                : keyword.trim();

        // userId가 null이면 전체 카드 조회
        return cardQueryJpaRepository.searchCards(null, tagId, solveStatus, normalizedKeyword, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CardListItemResponse> getMyCards(
            Long userId,
            int page,
            int size,
            String sort,
            String keyword,
            SolveStatus solveStatus
    ) {
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.max(size, 1),
                resolveSort(sort)
        );

        // keyword는 공백이면 null로 처리
        String normalizedKeyword = (keyword == null || keyword.trim().isEmpty())
                ? null
                : keyword.trim();

        // userId로 특정 사용자의 카드만 조회
        return cardQueryJpaRepository.searchCards(userId, null, solveStatus, normalizedKeyword, pageable);
    }

    private Sort resolveSort(String sort) {
        if (sort == null) return Sort.by(Sort.Direction.DESC, "createdAt");

        return switch (sort) {
            case "oldest" -> Sort.by(Sort.Direction.ASC, "createdAt");
            case "latest" -> Sort.by(Sort.Direction.DESC, "createdAt");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
    }
}