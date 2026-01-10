package com.example.anxiety_senpai.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProgressEvent {
    CREATE_CARD(40),
    WRITE_COMMENT(30),
    COMMENT_ACCEPTED(50),
    REACT_TO_OTHERS(5),
    RECEIVE_REACTION(5);

    private final int delta;
}

