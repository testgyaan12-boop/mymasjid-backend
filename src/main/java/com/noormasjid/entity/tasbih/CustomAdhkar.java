package com.noormasjid.entity.tasbih;

import com.noormasjid.entity.auth.User;
import com.noormasjid.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "custom_adhkars")
public class CustomAdhkar extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "arabic", columnDefinition = "TEXT")
    private String arabic;

    @Column(name = "transliteration", columnDefinition = "TEXT")
    private String transliteration;

    @Column(name = "meaning", columnDefinition = "TEXT")
    private String meaning;

    @Column(name = "color", length = 20)
    private String color;
}
