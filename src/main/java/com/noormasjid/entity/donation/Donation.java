package com.noormasjid.entity.donation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noormasjid.entity.auth.User;
import com.noormasjid.entity.base.BaseEntity;
import com.noormasjid.entity.masjid.Masjid;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "donations")
public class Donation extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "transaction_id", length = 255)
    private String transactionId;

    @Column(name = "donation_date")
    private LocalDateTime donationDate;

    @Column(name = "status", length = 20)
    private String status;
}
