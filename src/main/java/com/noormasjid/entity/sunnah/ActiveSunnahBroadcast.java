package com.noormasjid.entity.sunnah;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noormasjid.entity.base.BaseEntity;
import com.noormasjid.entity.masjid.Masjid;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "active_sunnah_broadcasts")
public class ActiveSunnahBroadcast extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sunnah_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "masjid"})
    private Sunnah sunnah;

    @Column(name = "broadcast_date", nullable = false)
    private LocalDate broadcastDate;
}
