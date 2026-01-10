package com.example.anxiety_senpai.domain.user.repository;

import com.example.anxiety_senpai.domain.user.entity.User;
import com.example.anxiety_senpai.domain.user.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialIdAndSocialType(
            String socialId,
            SocialType socialType
    );
}
