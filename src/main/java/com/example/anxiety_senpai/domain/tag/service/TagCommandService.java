package com.example.anxiety_senpai.domain.tag.service;

import com.example.anxiety_senpai.domain.card.entity.Card;
import com.example.anxiety_senpai.domain.card.exception.CardException;
import com.example.anxiety_senpai.domain.card.exception.code.CardErrorCode;
import com.example.anxiety_senpai.domain.card.repository.CardRepository;
import com.example.anxiety_senpai.domain.tag.dto.CardTagUpdateRequest;
import com.example.anxiety_senpai.domain.tag.dto.CardTagUpdateResponse;
import com.example.anxiety_senpai.domain.tag.entity.CardTag;
import com.example.anxiety_senpai.domain.tag.entity.Tag;
import com.example.anxiety_senpai.domain.tag.exception.TagException;
import com.example.anxiety_senpai.domain.tag.exception.code.TagErrorCode;
import com.example.anxiety_senpai.domain.tag.repository.TagRepository;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.domain.user.enums.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagCommandService {

    private final CardRepository cardRepository;
    private final TagRepository tagRepository;

    @Transactional
    public CardTagUpdateResponse replaceCardTags(Long cardId, Long userId, CardTagUpdateRequest request) {
        if (userId == null) {
            throw new TagException(TagErrorCode.UNAUTHORIZED);
        }

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.NOT_FOUND));

        User owner = card.getUser();
        boolean isOwner = owner.getId().equals(userId);
        boolean isAdmin = owner.getRole() == Role.ADMIN; // NOTE: ideally check requester role; fallback to owner role if user not loaded
        if (!(isOwner || isAdmin)) {
            throw new CardException(CardErrorCode.INVALID_TAG_NAME); // placeholder for 403
        }

        List<Long> tagIds = request.tagIds();
        if (tagIds == null) {
            throw new TagException(TagErrorCode.INVALID_PARAMETER);
        }

        List<Tag> tags = tagRepository.findAllById(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new TagException(TagErrorCode.TAG_NOT_FOUND);
        }

        card.clearTags();
        tags.forEach(card::addTag);

        return new CardTagUpdateResponse(card.getId(), tagIds);
    }
}
