package com.noormasjid.entity.cms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.noormasjid.entity.base.BaseEntity;
import com.noormasjid.entity.masjid.Masjid;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "gumshudas")
public class Gumshuda extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    @Column(name = "contact", length = 100)
    private String contact;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "found", nullable = false)
    private Boolean found = false;

    @JsonProperty("masjidName")
    public String getMasjidName() {
        return masjid != null ? masjid.getName() : null;
    }

    @JsonProperty("masjidId")
    public Long getMasjidId() {
        return masjid != null ? masjid.getId() : null;
    }
}



