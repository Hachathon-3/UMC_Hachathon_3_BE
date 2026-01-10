package com.example.anxiety_senpai.domain.tag.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CardTagId implements Serializable {
    private Long card; // CardTag.card 의 PK
    private Long tag;  // CardTag.tag 의 PK
}