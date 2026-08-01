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
@Table(name = "donation_causes")
public class DonationCause extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "upi", length = 100)
    private String upi;

    @Column(name = "badge", length = 100)
    private String badge;

    @Column(name = "qr_image", columnDefinition = "TEXT")
    private String qrImage;
}



