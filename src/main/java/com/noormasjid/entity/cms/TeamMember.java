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
@Table(name = "team_members")
public class TeamMember extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "role", length = 100)
    private String role;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "mobile", length = 20)
    private String mobile;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "responsibilities", columnDefinition = "TEXT")
    private String responsibilities;
}



