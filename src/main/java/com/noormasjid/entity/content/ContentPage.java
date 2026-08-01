package com.noormasjid.entity.content;

import com.noormasjid.entity.base.BaseEntity;
import com.noormasjid.entity.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "content_pages")
public class ContentPage extends BaseEntity {

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "slug", nullable = false, unique = true, length = 255)
    private String slug;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Column(name = "content_type", length = 50)
    private String contentType;

    @Column(name = "summary_ai", columnDefinition = "TEXT")
    private String summaryAi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "is_draft", nullable = false)
    private Boolean isDraft = true;
}
