package com.noormasjid.entity.quran;

import com.noormasjid.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ayats")
public class Ayat extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surah_id", nullable = false)
    private Surah surah;

    @Column(name = "ayat_number", nullable = false)
    private Integer ayatNumber;

    @Column(name = "text_arabic", columnDefinition = "TEXT")
    private String textArabic;

    @Column(name = "text_translation", columnDefinition = "TEXT")
    private String textTranslation;

    @Column(name = "text_transliteration", columnDefinition = "TEXT")
    private String textTransliteration;

    @Column(name = "audio_url", length = 500)
    private String audioUrl;
}
