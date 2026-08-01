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
@Table(name = "ramadan_config")
public class RamadanConfig extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @Column(name = "taraweeh", length = 100)
    private String taraweeh;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "iftar_message", columnDefinition = "TEXT")
    private String iftarMessage;

    @Column(name = "fitra_rate")
    private Double fitraRate;
}


