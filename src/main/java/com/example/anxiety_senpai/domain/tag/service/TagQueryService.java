package com.example.anxiety_senpai.domain.tag.service;

import com.example.anxiety_senpai.domain.card.dto.PageResponse;
import com.example.anxiety_senpai.domain.tag.dto.TagListItemResponse;
import com.example.anxiety_senpai.domain.tag.dto.TagListResponse;
import com.example.anxiety_senpai.domain.tag.entity.Tag;
import com.example.anxiety_senpai.domain.tag.exception.TagException;
import com.example.anxiety_senpai.domain.tag.exception.code.TagErrorCode;
import com.example.anxiety_senpai.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagQueryService {

    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public TagListResponse getTags(String keyword, int page, int size, String sort) {
        if (page < 0 || size <= 0) {
            throw new TagException(TagErrorCode.INVALID_PARAMETER);
        }

        Sort sortOption = switch (sort == null ? "" : sort.toLowerCase()) {
            case "name_asc" -> Sort.by(Sort.Direction.ASC, "name");
            case "latest" -> Sort.by(Sort.Direction.DESC, "createdAt");
            case "popular" -> Sort.unsorted(); // usageCount 구현 시 교체
            case "" -> Sort.by(Sort.Direction.ASC, "name"); // default: name asc
            default -> throw new TagException(TagErrorCode.INVALID_PARAMETER);
        };

        Pageable pageable = PageRequest.of(page, size, sortOption);

        Page<Tag> tagPage = tagRepository.searchByKeyword(keyword, pageable);

        Page<TagListItemResponse> mapped = tagPage.map(tag -> new TagListItemResponse(
                tag.getId(),
                tag.getName(),
                tag.getCardTags() == null ? 0 : tag.getCardTags().size()
        ));

        return TagListResponse.of(PageResponse.of(mapped));
    }
}

