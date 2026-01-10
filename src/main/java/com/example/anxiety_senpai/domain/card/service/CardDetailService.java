package com.example.anxiety_senpai.domain.card.service;

import com.example.anxiety_senpai.domain.card.dto.CardDetailResponse;
import com.example.anxiety_senpai.domain.card.entity.Card;
import com.example.anxiety_senpai.domain.card.enums.CardStatus;
import com.example.anxiety_senpai.domain.card.exception.CardException;
import com.example.anxiety_senpai.domain.card.exception.code.CardErrorCode;
import com.example.anxiety_senpai.domain.card.repository.CardDetailQueryRepository;
import com.example.anxiety_senpai.domain.reaction.repository.ReactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CardDetailService {

    private final CardDetailQueryRepository cardDetailQueryRepository;
    private final ReactionRepository reactionRepository;

    @Transactional
    public CardDetailResponse getCardDetail(Long cardId, Long userId) {

        Card card = cardDetailQueryRepository.findCardDetail(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.NOT_FOUND));

        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new CardException(CardErrorCode.NOT_FOUND);
        }

        long reactionCount = reactionRepository.countByCardId(cardId);
        boolean liked = userId != null && !reactionRepository.findByCardIdAndUserId(cardId, userId).isEmpty();

        List<CardDetailResponse.TagResponse> tags = card.getCardTags().stream()
                .map(ct -> new CardDetailResponse.TagResponse(ct.getTag().getId(), ct.getTag().getName()))
                .distinct()
                .toList();

        return new CardDetailResponse(
                card.getId(),
                card.getTitle(),
                card.getContent(),
                card.getStatus(),
                card.getSolveStatus(),
                Boolean.TRUE.equals(card.getAllowComment()),
                new CardDetailResponse.AuthorResponse(card.getUser().getId(), card.getUser().getName()),
                tags,
                reactionCount,
                liked,
                card.getCreatedAt(),
                card.getUpdatedAt(),
                null
        );
    }
}
