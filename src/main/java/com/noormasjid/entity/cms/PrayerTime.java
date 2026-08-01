package com.noormasjid.entity.cms;

import com.noormasjid.entity.base.BaseEntity;
import com.noormasjid.entity.masjid.Masjid;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "prayer_times")
public class PrayerTime extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @Column(name = "prayer_name", nullable = false, length = 50)
    private String prayerName;

    @Column(name = "azaan_time", length = 10)
    private String azaanTime;

    @Column(name = "prayer_time", length = 10)
    private String prayerTime;

    @Column(name = "hour")
    private Integer hour;

    @Column(name = "minute")
    private Integer minute;

    @Column(name = "icon", length = 50)
    private String icon;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @JsonIgnore
    public Masjid getMasjid() { return masjid; }
    public void setMasjid(Masjid masjid) { this.masjid = masjid; }

    @JsonProperty("name")
    public String getPrayerName() { return prayerName; }
    @JsonProperty("name")
    public void setPrayerName(String prayerName) { this.prayerName = prayerName; }

    @JsonProperty("azaan")
    public String getAzaanTime() { return azaanTime; }
    @JsonProperty("azaan")
    public void setAzaanTime(String azaanTime) { this.azaanTime = azaanTime; }

    @JsonProperty("time")
    public String getPrayerTime() { return prayerTime; }
    @JsonProperty("time")
    public void setPrayerTime(String prayerTime) { this.prayerTime = prayerTime; }

    public Integer getHour() { return hour; }
    public void setHour(Integer hour) { this.hour = hour; }

    public Integer getMinute() { return minute; }
    public void setMinute(Integer minute) { this.minute = minute; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}
