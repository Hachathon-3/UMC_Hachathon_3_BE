package com.example.anxiety_senpai.domain.card.service;

import com.example.anxiety_senpai.domain.card.dto.CardListItemResponse;
import com.example.anxiety_senpai.domain.card.enums.SolveStatus;
import com.example.anxiety_senpai.domain.card.exception.CardException;
import com.example.anxiety_senpai.domain.card.exception.code.CardErrorCode;
import com.example.anxiety_senpai.domain.card.repository.CardQueryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
        if (page < 0 || size <= 0) {
            throw new CardException(CardErrorCode.INVALID_TAG_NAME); // placeholder 400
        }

        Pageable pageable = PageRequest.of(page, size, resolveSort(sort));

        String normalizedKeyword = (StringUtils.hasText(keyword)) ? keyword.trim() : null;

        if ("popular".equalsIgnoreCase(sort)) {
            return cardQueryJpaRepository.searchCardsOrderByReactionDesc(tagId, solveStatus, normalizedKeyword, pageable);
        }
        return cardQueryJpaRepository.searchCards(tagId, solveStatus, normalizedKeyword, pageable);
    }

    private Sort resolveSort(String sort) {
        if (sort == null) return Sort.by(Sort.Direction.DESC, "createdAt");

        return switch (sort) {
            case "oldest" -> Sort.by(Sort.Direction.ASC, "createdAt");
            case "latest" -> Sort.by(Sort.Direction.DESC, "createdAt");
            case "popular" -> Sort.unsorted();
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
    }
}