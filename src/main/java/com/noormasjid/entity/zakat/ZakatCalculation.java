package com.noormasjid.entity.zakat;

import com.noormasjid.entity.auth.User;
import com.noormasjid.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "zakat_calculations")
public class ZakatCalculation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "gold_value")
    private Double goldValue;

    @Column(name = "silver_value")
    private Double silverValue;

    @Column(name = "cash_value")
    private Double cashValue;

    @Column(name = "business_value")
    private Double businessValue;

    @Column(name = "property_value")
    private Double propertyValue;

    @Column(name = "liabilities")
    private Double liabilities;

    @Column(name = "net_worth")
    private Double netWorth;

    @Column(name = "zakat_due")
    private Double zakatDue;

    @Column(name = "zakat_type", length = 50)
    private String zakatType;

    @Column(name = "calculation_date", nullable = false)
    private java.time.LocalDate calculationDate;
}
