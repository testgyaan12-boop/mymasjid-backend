package com.noormasjid.entity.auth;

import com.noormasjid.entity.base.BaseEntity;
import com.noormasjid.entity.masjid.Masjid;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_masjid_roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "masjid_id"})
})
public class UserMasjidRole extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
