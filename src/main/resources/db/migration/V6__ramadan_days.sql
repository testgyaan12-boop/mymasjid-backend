-- ============================================================
-- Noor Al Masjid - Day-wise Ramadan Schedule (Sehri & Iftar)
-- ============================================================

CREATE TABLE ramadan_days (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    day_no INT NOT NULL,
    sehri_end VARCHAR(10),
    iftar_time VARCHAR(10)
);

CREATE INDEX idx_ramadan_days_masjid ON ramadan_days(masjid_id);