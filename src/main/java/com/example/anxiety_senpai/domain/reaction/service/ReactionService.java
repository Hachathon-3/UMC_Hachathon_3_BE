package com.example.anxiety_senpai.domain.reaction.service;

import com.example.anxiety_senpai.domain.card.entity.Card;
import com.example.anxiety_senpai.domain.card.enums.CardStatus;
import com.example.anxiety_senpai.domain.card.repository.CardRepository;
import com.example.anxiety_senpai.domain.reaction.dto.ReactionResponse;
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
}

