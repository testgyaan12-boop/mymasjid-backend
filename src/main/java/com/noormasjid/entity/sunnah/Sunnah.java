package com.noormasjid.entity.sunnah;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.noormasjid.entity.base.BaseEntity;
import com.noormasjid.entity.masjid.Masjid;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sunnahs")
public class Sunnah extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "arabic", columnDefinition = "TEXT")
    private String arabic;

    @Column(name = "transliteration", columnDefinition = "TEXT")
    private String transliteration;

    @Column(name = "meaning", columnDefinition = "TEXT")
    private String meaning;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "reference", length = 255)
    private String reference;

    @Column(name = "sunnah_text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;
}
