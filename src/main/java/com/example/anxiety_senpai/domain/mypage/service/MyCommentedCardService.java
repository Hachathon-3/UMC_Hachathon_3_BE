package com.example.anxiety_senpai.domain.mypage.service;

import com.example.anxiety_senpai.domain.cardComment.repository.CardCommentRepository;
import com.example.anxiety_senpai.domain.mypage.dto.CommentedCardResponse;
import com.example.anxiety_senpai.domain.mypage.exception.MyPageException;
import com.example.anxiety_senpai.domain.mypage.exception.code.MyPageErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyCommentedCardService {

    private final CardCommentRepository cardCommentRepository;

    @Transactional(readOnly = true)
    public Page<CommentedCardResponse> getCommentedCards(Long userId, int page, int size, String sort) {
        if (userId == null) {
            throw new MyPageException(MyPageErrorCode.UNAUTHORIZED);
        }
        if (page < 0 || size <= 0) {
            throw new MyPageException(MyPageErrorCode.BAD_REQUEST);
        }

        Sort sortOption = switch (sort == null ? "" : sort.toLowerCase()) {
            case "card_latest" -> Sort.by(Sort.Direction.DESC, "c.createdAt");
            case "latest_comment", "" -> Sort.by(Sort.Direction.DESC, "lastCommentedAt");
            default -> throw new MyPageException(MyPageErrorCode.BAD_REQUEST);
        };

        Pageable pageable = PageRequest.of(page, size, sortOption);
        return cardCommentRepository.findCommentedCards(userId, pageable);
    }
}

