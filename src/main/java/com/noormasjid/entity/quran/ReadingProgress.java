package com.noormasjid.entity.quran;

import com.noormasjid.entity.auth.User;
import com.noormasjid.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reading_progress")
public class ReadingProgress extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "completed_ayats", columnDefinition = "TEXT")
    private String completedAyats;

    @Column(name = "completed_juzs", columnDefinition = "TEXT")
    private String completedJuzs;

    @Column(name = "last_read_surah")
    private Integer lastReadSurah;

    @Column(name = "last_read_juz")
    private Integer lastReadJuz;

    @Column(name = "last_read_page")
    private Integer lastReadPage;

    @Column(name = "last_read_mode", length = 20)
    private String lastReadMode;

    @Column(name = "surah_checkpoints", columnDefinition = "TEXT")
    private String surahCheckpoints;

    @Column(name = "juz_checkpoints", columnDefinition = "TEXT")
    private String juzCheckpoints;

    @Column(name = "session_time")
    private Long sessionTime;
}
