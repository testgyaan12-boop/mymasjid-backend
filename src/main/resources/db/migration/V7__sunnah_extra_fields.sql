-- ============================================================
-- Noor Al Masjid - Sunnah extra fields (text, reference, image)
-- ============================================================

ALTER TABLE sunnahs ADD COLUMN reference VARCHAR(255);
ALTER TABLE sunnahs ADD COLUMN sunnah_text TEXT;
ALTER TABLE sunnahs ADD COLUMN image TEXT;