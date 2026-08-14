package com.noormasjid.entity.notification;

import com.noormasjid.entity.base.BaseEntity;
import com.noormasjid.entity.masjid.Masjid;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "prayer_reminder_log",
        uniqueConstraints = @UniqueConstraint(name = "uq_prayer_reminder",
                columnNames = {"masjid_id", "prayer_name", "reminder_date"}))
public class PrayerReminderLog extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @Column(name = "prayer_name", nullable = false, length = 50)
    private String prayerName;

    @Column(name = "reminder_date", nullable = false)
    private LocalDate reminderDate;

    @Column(name = "iqamah_time", length = 10)
    private String iqamahTime;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;
}