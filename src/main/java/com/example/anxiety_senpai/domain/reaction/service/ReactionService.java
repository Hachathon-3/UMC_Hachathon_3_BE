package com.example.anxiety_senpai.domain.reaction.service;

import com.example.anxiety_senpai.domain.card.entity.Card;
import com.example.anxiety_senpai.domain.card.enums.CardStatus;
import com.example.anxiety_senpai.domain.card.repository.CardRepository;
import com.example.anxiety_senpai.domain.cardComment.entity.CardComment;
import com.example.anxiety_senpai.domain.cardComment.repository.CardCommentRepository;
import com.example.anxiety_senpai.domain.reaction.dto.ReactionResponse;
import com.example.anxiety_senpai.domain.reaction.dto.ReactionSummaryResponse;
import com.example.anxiety_senpai.domain.reaction.entity.Reaction;
import com.example.anxiety_senpai.domain.reaction.exception.ReactionException;
import com.example.anxiety_senpai.domain.reaction.exception.code.ReactionErrorCode;
import com.example.anxiety_senpai.domain.reaction.repository.ReactionRepository;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final CardRepository cardRepository;
    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final CardCommentRepository cardCommentRepository;

    @Transactional
    public ReactionResponse reactToCard(Long cardId, Long userId) {
        if (userId == null) {
            throw new ReactionException(ReactionErrorCode.UNAUTHORIZED);
        }

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ReactionException(ReactionErrorCode.NOT_FOUND_CARD));
        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new ReactionException(ReactionErrorCode.NOT_FOUND_CARD);
        }

        if (reactionRepository.existsByCardIdAndUserId(cardId, userId)) {
            throw new ReactionException(ReactionErrorCode.DUPLICATE_REACTION);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ReactionException(ReactionErrorCode.UNAUTHORIZED));

        reactionRepository.save(Reaction.builder()
                .card(card)
                .user(user)
                .build());

        return new ReactionResponse(cardId, true);
    }

    @Transactional(readOnly = true)
    public ReactionSummaryResponse getCardReactionSummary(Long cardId, Long userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ReactionException(ReactionErrorCode.NOT_FOUND_CARD));
        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new ReactionException(ReactionErrorCode.NOT_FOUND_CARD);
        }

        long count = reactionRepository.countByCardId(cardId);
        boolean myReacted = (userId != null) && reactionRepository.existsByCardIdAndUserId(cardId, userId);

        return new ReactionSummaryResponse(cardId, null, count, myReacted);
    }

    @Transactional(readOnly = true)
    public ReactionSummaryResponse getCommentReactionSummary(Long commentId, Long userId) {
        CardComment comment = cardCommentRepository.findById(commentId)
                .orElseThrow(() -> new ReactionException(ReactionErrorCode.NOT_FOUND_COMMENT));

        long count = reactionRepository.countByCommentId(commentId);
        boolean myReacted = (userId != null) && reactionRepository.existsByCardCommentIdAndUserId(commentId, userId);

        return new ReactionSummaryResponse(null, commentId, count, myReacted);
    }

    @Transactional
    public ReactionResponse reactToComment(Long commentId, Long userId) {
        if (userId == null) {
            throw new ReactionException(ReactionErrorCode.UNAUTHORIZED);
        }

        CardComment comment = cardCommentRepository.findById(commentId)
                .orElseThrow(() -> new ReactionException(ReactionErrorCode.NOT_FOUND_COMMENT));

        if (reactionRepository.existsByCardCommentIdAndUserId(commentId, userId)) {
            throw new ReactionException(ReactionErrorCode.DUPLICATE_COMMENT_REACTION);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ReactionException(ReactionErrorCode.UNAUTHORIZED));

        reactionRepository.save(Reaction.builder()
                .cardComment(comment)
                .user(user)
                .build());

        return new ReactionResponse(commentId, true);
    }
}
