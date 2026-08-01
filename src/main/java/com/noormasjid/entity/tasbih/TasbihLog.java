package com.noormasjid.entity.tasbih;

import com.noormasjid.entity.auth.User;
import com.noormasjid.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tasbih_logs")
public class TasbihLog extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "dhikr", length = 255)
    private String dhikr;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "session_date", nullable = false)
    private java.time.LocalDate sessionDate;
}
