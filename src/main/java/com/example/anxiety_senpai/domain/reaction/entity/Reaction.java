package com.example.anxiety_senpai.domain.reaction.entity;

import com.example.anxiety_senpai.domain.card.entity.Card;
import com.example.anxiety_senpai.domain.cardComment.entity.CardComment;
import com.example.anxiety_senpai.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "reactions")
@Check(constraints = "(card_id is not null) OR (card_comment_id is not null)")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_comment_id")
    private CardComment cardComment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
