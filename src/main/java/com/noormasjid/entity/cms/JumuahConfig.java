package com.noormasjid.entity.cms;

import com.noormasjid.entity.base.BaseEntity;
import com.noormasjid.entity.masjid.Masjid;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "jumuah_config")
public class JumuahConfig extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @Column(name = "prayer_time", length = 10)
    private String prayerTime;

    @Column(name = "azaan_time", length = 10)
    private String azaanTime;

    @JsonIgnore
    public Masjid getMasjid() { return masjid; }
    public void setMasjid(Masjid masjid) { this.masjid = masjid; }

    @JsonProperty("time")
    public String getPrayerTime() { return prayerTime; }
    @JsonProperty("time")
    public void setPrayerTime(String prayerTime) { this.prayerTime = prayerTime; }

    public String getAzaanTime() { return azaanTime; }
    public void setAzaanTime(String azaanTime) { this.azaanTime = azaanTime; }
}
