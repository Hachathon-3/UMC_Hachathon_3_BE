package com.example.anxiety_senpai.domain.user.service;

import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.domain.user.exception.UserException;
import com.example.anxiety_senpai.domain.user.exception.code.UserErrorCode;
import com.example.anxiety_senpai.domain.user.repository.UserRepository;
import com.example.anxiety_senpai.domain.user.enums.ProgressEvent;
import com.example.anxiety_senpai.domain.user.dto.HomeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void increaseProgress(Long userId, int delta) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        user.addProgress(delta);
    }

    @Transactional
    public void increaseProgress(Long userId, ProgressEvent event) {
        if (event == null) return;
        increaseProgress(userId, event.getDelta());
    }

    @Transactional(readOnly = true)
    public HomeResponse getHome(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        return new HomeResponse(user.getProgressPercent(), user.getCatnipCount());
    }
}
