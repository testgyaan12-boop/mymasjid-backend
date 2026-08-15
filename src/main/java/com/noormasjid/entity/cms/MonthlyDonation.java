package com.noormasjid.entity.cms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.noormasjid.entity.base.BaseEntity;
import com.noormasjid.entity.masjid.Masjid;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "monthly_donations")
public class MonthlyDonation extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @Column(name = "month", length = 20)
    private String month;

    @Column(name = "donation_date")
    private LocalDate donationDate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status", length = 20)
    private String status;
}



