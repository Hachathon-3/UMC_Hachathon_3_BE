package com.example.anxiety_senpai.domain.tag.service;

import com.example.anxiety_senpai.domain.card.entity.Card;
import com.example.anxiety_senpai.domain.card.exception.CardException;
import com.example.anxiety_senpai.domain.card.exception.code.CardErrorCode;
import com.example.anxiety_senpai.domain.card.repository.CardRepository;
import com.example.anxiety_senpai.domain.tag.dto.CardTagAddResponse;
import com.example.anxiety_senpai.domain.tag.dto.CardTagUpdateRequest;
import com.example.anxiety_senpai.domain.tag.dto.CardTagUpdateResponse;
import com.example.anxiety_senpai.domain.tag.dto.CardTagRemoveResponse;
import com.example.anxiety_senpai.domain.tag.entity.CardTag;
import com.example.anxiety_senpai.domain.tag.entity.Tag;
import com.example.anxiety_senpai.domain.tag.exception.TagException;
import com.example.anxiety_senpai.domain.tag.exception.code.TagErrorCode;
import com.example.anxiety_senpai.domain.tag.exception.code.TagSuccessCode;
import com.example.anxiety_senpai.domain.tag.repository.TagRepository;
import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.domain.user.enums.Role;
import com.example.anxiety_senpai.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagCommandService {

    private final CardRepository cardRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    private void assertOwnerOrAdmin(Card card, Long requesterId) {
        if (requesterId == null) {
            throw new TagException(TagErrorCode.UNAUTHORIZED);
        }
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new TagException(TagErrorCode.UNAUTHORIZED));
        boolean isOwner = card.getUser().getId().equals(requesterId);
        boolean isAdmin = requester.getRole() == Role.ADMIN;
        if (!(isOwner || isAdmin)) {
            throw new CardException(CardErrorCode.FORBIDDEN);
        }
    }

    @Transactional
    public CardTagUpdateResponse replaceCardTags(Long cardId, Long userId, CardTagUpdateRequest request) {
        if (request == null || request.tagIds() == null) {
            throw new TagException(TagErrorCode.INVALID_PARAMETER);
        }

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.NOT_FOUND));

        assertOwnerOrAdmin(card, userId);

        List<Long> tagIds = request.tagIds();
        List<Tag> tags = tagRepository.findAllById(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new TagException(TagErrorCode.TAG_NOT_FOUND);
        }

        card.clearTags();
        tags.forEach(card::addTag);

        return new CardTagUpdateResponse(card.getId(), tagIds);
    }

    @Transactional
    public CardTagAddResponse addTagToCard(Long cardId, Long userId, Long tagId, boolean failOnDuplicate) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.NOT_FOUND));

        assertOwnerOrAdmin(card, userId);

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagException(TagErrorCode.TAG_NOT_FOUND));

        boolean alreadyLinked = card.getCardTags().stream()
                .anyMatch(ct -> ct.getTag().getId().equals(tagId));
        if (alreadyLinked) {
            if (failOnDuplicate) {
                throw new TagException(TagErrorCode.DUPLICATE_CARD_TAG);
            }
            return new CardTagAddResponse(card.getId(), tagId);
        }

        card.addTag(tag);
        return new CardTagAddResponse(card.getId(), tagId);
    }

    @Transactional
    public CardTagRemoveResponse removeTagFromCard(Long cardId, Long userId, Long tagId, boolean failIfMissing) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.NOT_FOUND));

        assertOwnerOrAdmin(card, userId);

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagException(TagErrorCode.TAG_NOT_FOUND));

        boolean linked = card.getCardTags().stream()
                .anyMatch(ct -> ct.getTag().getId().equals(tagId));
        if (!linked) {
            if (failIfMissing) {
                throw new TagException(TagErrorCode.CARD_TAG_NOT_FOUND);
            }
            return new CardTagRemoveResponse(card.getId(), tagId);
        }

        // 실제 제거: Card#clearTags는 전체이므로, 선택적으로 제거하기 위해 카드의 CardTag 리스트를 필터링
        card.getCardTags().removeIf(ct -> ct.getTag().getId().equals(tagId));
        return new CardTagRemoveResponse(card.getId(), tagId);
    }
}
