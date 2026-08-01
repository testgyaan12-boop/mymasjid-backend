package com.noormasjid.repository;

import com.noormasjid.entity.content.ContentPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentPageRepository extends JpaRepository<ContentPage, Long> {
    List<ContentPage> findByIsDraftAndIsDeleted(Boolean isDraft, Integer isDeleted);
    Optional<ContentPage> findBySlugAndIsDeleted(String slug, Integer isDeleted);
}
