package com.example.anxiety_senpai.domain.cardComment.service;

import com.example.anxiety_senpai.domain.card.entity.Card;
import com.example.anxiety_senpai.domain.card.enums.CardStatus;
import com.example.anxiety_senpai.domain.card.repository.CardRepository;
import com.example.anxiety_senpai.domain.cardComment.dto.CardCommentCreateRequest;
import com.example.anxiety_senpai.domain.cardComment.dto.CardCommentCreateResponse;
import com.example.anxiety_senpai.domain.cardComment.dto.CardCommentListResponse;
import com.example.anxiety_senpai.domain.cardComment.dto.CardCommentQueryResponse;
import com.example.anxiety_senpai.domain.cardComment.entity.CardComment;
import com.example.anxiety_senpai.domain.cardComment.exception.CardCommentException;
import com.example.anxiety_senpai.domain.cardComment.exception.code.CardCommentErrorCode;
import com.example.anxiety_senpai.domain.cardComment.repository.CardCommentRepository;
import com.example.anxiety_senpai.domain.card.dto.PageResponse;
import com.example.anxiety_senpai.domain.cardComment.enums.CardCommentStatus;
import com.example.anxiety_senpai.domain.reaction.enums.ReactionType;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CardCommentService {

    private final CardRepository cardRepository;
    private final CardCommentRepository cardCommentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CardCommentCreateResponse createComment(Long cardId, Long userId, CardCommentCreateRequest request) {
        if (userId == null) {
            throw new CardCommentException(CardCommentErrorCode.UNAUTHORIZED);
        }
        if (request == null || request.content() == null || request.content().trim().isEmpty()) {
            throw new CardCommentException(CardCommentErrorCode.INVALID_CONTENT);
        }

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardCommentException(CardCommentErrorCode.NOT_FOUND_CARD));

        if (card.getStatus() != CardStatus.ACTIVE || Boolean.FALSE.equals(card.getAllowComment())) {
            throw new CardCommentException(CardCommentErrorCode.FORBIDDEN);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CardCommentException(CardCommentErrorCode.UNAUTHORIZED));

        CardComment saved = cardCommentRepository.save(CardComment.builder()
                .card(card)
                .user(user)
                .content(request.content().trim())
                .build());

        return new CardCommentCreateResponse(saved.getId());
    }

    @Transactional(readOnly = true)
    public CardCommentListResponse getComments(Long cardId, Long userId, int page, int size, String sort) {
        if (page < 0 || size <= 0) {
            throw new CardCommentException(CardCommentErrorCode.INVALID_PARAMETER);
        }

        Sort sortOption = switch (sort == null ? "" : sort.toLowerCase()) {
            case "oldest" -> Sort.by(Sort.Direction.ASC, "createdAt");
            case "latest", "" -> Sort.by(Sort.Direction.DESC, "createdAt");
            default -> throw new CardCommentException(CardCommentErrorCode.INVALID_PARAMETER);
        };
        Pageable pageable = PageRequest.of(page, size, sortOption);

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardCommentException(CardCommentErrorCode.NOT_FOUND_CARD));
        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new CardCommentException(CardCommentErrorCode.NOT_FOUND_CARD);
        }

        Page<CardComment> commentPage = cardCommentRepository.findByCardIdAndStatus(cardId, CardCommentStatus.ACTIVE, pageable);

        Map<Long, Map<ReactionType, Long>> summaryByComment = Map.of();
        Map<Long, Set<ReactionType>> myReactionsByComment = Map.of();
        // Reaction per comment not implemented in current schema; return empty aggregates.

        Page<CardCommentQueryResponse> mapped = commentPage.map(comment -> new CardCommentQueryResponse(
                comment.getId(),
                comment.getCard().getId(),
                comment.getContent(),
                comment.getStatus(),
                new CardCommentQueryResponse.Author(comment.getUser().getId(), comment.getUser().getName()),
                summaryByComment.getOrDefault(comment.getId(), Map.of()),
                myReactionsByComment.getOrDefault(comment.getId(), Set.of()),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getDeletedAt()
        ));

        return CardCommentListResponse.of(PageResponse.of(mapped));
    }
}
