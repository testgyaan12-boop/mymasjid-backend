package com.noormasjid.entity.masjid;

import com.noormasjid.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "masjids")
public class Masjid extends BaseEntity {

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "pincode", nullable = false, length = 10)
    private String pincode;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "logo", length = 500)
    private String logo;

    @Column(name = "website", length = 500)
    private String website;

    @Column(name = "about", columnDefinition = "TEXT")
    private String about;

    @Column(name = "vision", columnDefinition = "TEXT")
    private String vision;
}
