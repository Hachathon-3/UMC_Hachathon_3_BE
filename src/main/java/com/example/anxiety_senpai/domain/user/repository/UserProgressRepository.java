package com.example.anxiety_senpai.domain.user.repository;

import com.example.anxiety_senpai.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserProgressRepository extends JpaRepository<User, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        update User u
        set u.progressPercent = mod(u.progressPercent + :delta, 100),
            u.catnipCount = u.catnipCount + ((u.progressPercent + :delta) / 100)
        where u.id = :userId
    """)
    int increaseProgressAndCatnip(
            @Param("userId") Long userId,
            @Param("delta") int delta
    );
}
