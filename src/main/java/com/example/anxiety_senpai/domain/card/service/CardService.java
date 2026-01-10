package com.example.anxiety_senpai.domain.card.service;

import com.example.anxiety_senpai.domain.card.dto.CardCreateRequest;
import com.example.anxiety_senpai.domain.card.entity.Card;
import com.example.anxiety_senpai.domain.card.exception.CardException;
import com.example.anxiety_senpai.domain.card.exception.code.CardErrorCode;
import com.example.anxiety_senpai.domain.card.repository.CardRepository;
import com.example.anxiety_senpai.domain.tag.entity.Tag;
import com.example.anxiety_senpai.domain.tag.repository.TagRepository;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public Long create(Long userId, CardCreateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CardException(CardErrorCode.USER_NOT_FOUND));

        Card card = Card.builder()
                .user(user)
                .title(request.title())
                .content(request.content())
                .allowComment(
                        request.allowComment() != null ? request.allowComment() : true
                )
                .build();

        // 태그 처리 (자동 생성)
        if (request.tagNames() != null && !request.tagNames().isEmpty()) {
            attachTags(card, request.tagNames());
        }

        Card saved = cardRepository.save(card);
        return saved.getId();
    }

    private void attachTags(Card card, List<String> tagNames) {
        for (String name : tagNames) {
            String normalized = name.trim().toLowerCase();

            if (normalized.isEmpty()) {
                throw new CardException(CardErrorCode.INVALID_TAG_NAME);
            }

            Tag tag = tagRepository.findByName(normalized)
                    .orElseGet(() ->
                            tagRepository.save(
                                    Tag.builder()
                                            .name(normalized)
                                            .build()
                            )
                    );

            card.addTag(tag);
        }
    }
}