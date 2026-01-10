package com.example.anxiety_senpai.domain.cardComment.service;

import com.example.anxiety_senpai.domain.card.entity.Card;
import com.example.anxiety_senpai.domain.card.enums.CardStatus;
import com.example.anxiety_senpai.domain.card.repository.CardRepository;
import com.example.anxiety_senpai.domain.cardComment.dto.CardCommentCreateRequest;
import com.example.anxiety_senpai.domain.cardComment.dto.CardCommentCreateResponse;
import com.example.anxiety_senpai.domain.cardComment.entity.CardComment;
import com.example.anxiety_senpai.domain.cardComment.exception.CardCommentException;
import com.example.anxiety_senpai.domain.cardComment.exception.code.CardCommentErrorCode;
import com.example.anxiety_senpai.domain.cardComment.repository.CardCommentRepository;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

