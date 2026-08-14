-- ============================================================
-- Noor Al Masjid - Prayer Time Reminder Log
-- Tracks which masjid/prayer/day already had a pre-iqamah reminder sent
-- so the scheduler never duplicates notifications.
-- ============================================================

CREATE TABLE prayer_reminder_log (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    prayer_name VARCHAR(50) NOT NULL,
    reminder_date DATE NOT NULL,
    iqamah_time VARCHAR(10),
    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_prayer_reminder UNIQUE (masjid_id, prayer_name, reminder_date)
);