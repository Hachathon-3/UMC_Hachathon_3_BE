package com.example.anxiety_senpai.domain.tag.repository;

import com.example.anxiety_senpai.domain.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query("""
        select t
        from Tag t
        where (:keyword is null or lower(t.name) like lower(concat('%', :keyword, '%')))
    """)
    Page<Tag> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
