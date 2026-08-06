-- ============================================================
-- Noor Al Masjid - In-App Notification History
-- ============================================================

CREATE TABLE app_notifications (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT REFERENCES masjids(id),
    user_id BIGINT REFERENCES users(id),
    title VARCHAR(255) NOT NULL,
    message TEXT,
    type VARCHAR(30) DEFAULT 'alert',
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    read_at TIMESTAMP
);
