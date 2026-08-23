package com.noormasjid.entity.cms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.noormasjid.entity.base.BaseEntity;
import com.noormasjid.entity.masjid.Masjid;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "janazahs")
public class Janazah extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "time", length = 50)
    private String time;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Transient
    @JsonProperty("masjidName")
    public String getMasjidNameJson() {
        try {
            return masjid != null ? masjid.getName() : null;
        } catch (Exception e) {
            return null;
        }
    }

    @Transient
    @JsonProperty("masjidId")
    public Long getMasjidIdJson() {
        try {
            return masjid != null ? masjid.getId() : null;
        } catch (Exception e) {
            return null;
        }
    }
}



