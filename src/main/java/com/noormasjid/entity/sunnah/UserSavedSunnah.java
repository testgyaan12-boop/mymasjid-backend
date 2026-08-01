package com.noormasjid.entity.sunnah;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noormasjid.entity.auth.User;
import com.noormasjid.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_saved_sunnahs", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "sunnah_id"})
})
public class UserSavedSunnah extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sunnah_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "masjid"})
    private Sunnah sunnah;
}
