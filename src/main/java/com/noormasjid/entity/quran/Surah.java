package com.noormasjid.entity.quran;

import com.noormasjid.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "surahs")
public class Surah extends BaseEntity {

    @Column(name = "surah_number", nullable = false, unique = true)
    private Integer surahNumber;

    @Column(name = "name_arabic", length = 100)
    private String nameArabic;

    @Column(name = "name_english", length = 100)
    private String nameEnglish;

    @Column(name = "revelation_place", length = 20)
    private String revelationPlace;

    @Column(name = "number_of_ayats")
    private Integer numberOfAyats;
}
