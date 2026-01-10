package com.example.anxiety_senpai.domain.card.service;

import com.example.anxiety_senpai.domain.card.dto.MyCardStatsResponse;
import com.example.anxiety_senpai.domain.card.enums.SolveStatus;
import com.example.anxiety_senpai.domain.card.repository.CardRepository;
import com.example.anxiety_senpai.domain.cardComment.repository.CardCommentRepository;
import com.example.anxiety_senpai.global.apiPayload.code.GeneralErrorCode;
import com.example.anxiety_senpai.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardStatsService {

    private final CardRepository cardRepository;
    private final CardCommentRepository cardCommentRepository;

    @Transactional(readOnly = true)
    public MyCardStatsResponse getMyStats(Long userId) {
        if (userId == null) {
            throw new GeneralException(GeneralErrorCode.UNAUTHORIZED);
        }
        long total = cardRepository.countByUserId(userId);
        long solved = cardRepository.countByUserIdAndSolveStatus(userId, SolveStatus.SOLVED);
        long unsolved = cardRepository.countByUserIdAndSolveStatus(userId, SolveStatus.UNSOLVED);
        long commented = cardCommentRepository.countDistinctCardByUserId(userId);
        return new MyCardStatsResponse(total, solved, unsolved, commented);
    }
}

