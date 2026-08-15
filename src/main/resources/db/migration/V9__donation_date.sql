-- ============================================================
-- Noor Al Masjid - Collections use a donation date instead of month
-- ============================================================

ALTER TABLE monthly_donations ADD COLUMN donation_date DATE;

UPDATE monthly_donations SET donation_date = CURRENT_DATE WHERE donation_date IS NULL;