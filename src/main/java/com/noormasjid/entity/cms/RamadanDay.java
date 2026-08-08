package com.noormasjid.entity.cms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.noormasjid.entity.base.BaseEntity;
import com.noormasjid.entity.masjid.Masjid;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ramadan_days")
public class RamadanDay extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @Column(name = "day_no", nullable = false)
    private Integer dayNo;

    @Column(name = "sehri_end", length = 10)
    private String sehriEnd;

    @Column(name = "iftar_time", length = 10)
    private String iftarTime;
}