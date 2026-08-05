-- ============================================================
-- Noor Al Masjid - Push Notification Device Tokens
-- ============================================================

CREATE TABLE device_tokens (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    token TEXT NOT NULL,
    platform VARCHAR(20),
    CONSTRAINT uk_device_token UNIQUE (token)
);