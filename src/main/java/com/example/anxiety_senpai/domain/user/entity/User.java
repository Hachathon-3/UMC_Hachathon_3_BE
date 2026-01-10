package com.example.anxiety_senpai.domain.user.entity;


import com.example.anxiety_senpai.domain.user.enums.Role;
import com.example.anxiety_senpai.domain.user.enums.SocialType;
import com.example.anxiety_senpai.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import static com.example.anxiety_senpai.domain.user.enums.Role.USER;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_social",
                        columnNames = {"social_Type", "social_id"}
                )
        }
)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 id값 생성
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_Type", nullable = false, length = 30)
    private SocialType socialType;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Role role = USER;

    @Column(name = "progress_percent", nullable = false)
    @Builder.Default
    private Integer progressPercent = 0;

    @Column(name = "catnip_count", nullable = false)
    @Builder.Default
    private Integer catnipCount = 0;

    public static User createSocialUser(
            SocialType socialType,
            String socialId
    ) {
        return User.builder()
                .socialType(socialType)
                .socialId(socialId)
                .role(USER)
                .progressPercent(0)
                .catnipCount(0)
                .build();
    }

    public void addProgress(int delta) {
        if (delta <= 0) return;
        int updated = this.progressPercent + delta;
        int gainedCatnip = updated / 100;
        this.progressPercent = updated % 100;
        this.catnipCount += gainedCatnip;
    }
}