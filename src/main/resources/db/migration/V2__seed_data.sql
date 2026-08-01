-- ============================================================
-- Seed default roles
-- ============================================================
INSERT INTO roles (name, description, is_deleted, is_active) VALUES
('MANAGEMENT', 'Full access to all masjid settings and member management', 0, TRUE),
('ADMIN', 'Manage content, donations, and team members', 0, TRUE),
('EMPLOYEE', 'Manage prayer times and alerts', 0, TRUE),
('MEMBER', 'View masjid content and use personal features', 0, TRUE);

-- ============================================================
-- Seed default permissions
-- ============================================================
INSERT INTO permissions (name, description, module, is_deleted, is_active) VALUES
('prayer_time:read', 'View prayer times', 'prayer', 0, TRUE),
('prayer_time:write', 'Update prayer times', 'prayer', 0, TRUE),
('ramadan:write', 'Update ramadan config', 'prayer', 0, TRUE),
('alert:manage', 'Create/edit/delete alerts', 'alerts', 0, TRUE),
('announcement:read', 'View announcements', 'alerts', 0, TRUE),
('announcement:write', 'Create/edit/delete announcements', 'alerts', 0, TRUE),
('donation:read', 'View donations and expenses', 'donation', 0, TRUE),
('donation:manage', 'Manage donation causes, records, expenses', 'donation', 0, TRUE),
('branding:write', 'Update masjid branding', 'config', 0, TRUE),
('team:write', 'Manage about services', 'team', 0, TRUE),
('team:manage', 'Manage team members', 'team', 0, TRUE),
('member:read', 'View member list', 'members', 0, TRUE),
('member:manage', 'Assign/change member roles', 'members', 0, TRUE),
('sunnah:read', 'View sunnah library', 'sunnah', 0, TRUE),
('sunnah:manage', 'Manage sunnah library and broadcasts', 'sunnah', 0, TRUE),
('role:manage', 'Manage roles and permissions', 'admin', 0, TRUE);

-- ============================================================
-- Assign all permissions to MANAGEMENT
-- ============================================================
INSERT INTO role_permissions (role_id, permission_id, is_deleted, is_active)
SELECT r.id, p.id, 0, TRUE
FROM roles r, permissions p
WHERE r.name = 'MANAGEMENT';

-- ============================================================
-- Assign permissions to ADMIN
-- ============================================================
INSERT INTO role_permissions (role_id, permission_id, is_deleted, is_active)
SELECT r.id, p.id, 0, TRUE
FROM roles r, permissions p
WHERE r.name = 'ADMIN'
  AND p.name NOT IN ('member:manage', 'team:manage', 'role:manage');

-- ============================================================
-- Assign permissions to EMPLOYEE
-- ============================================================
INSERT INTO role_permissions (role_id, permission_id, is_deleted, is_active)
SELECT r.id, p.id, 0, TRUE
FROM roles r, permissions p
WHERE r.name = 'EMPLOYEE'
  AND p.name IN ('prayer_time:read', 'prayer_time:write', 'alert:manage',
                 'announcement:read', 'announcement:write', 'sunnah:read',
                 'donation:read');

-- ============================================================
-- Assign permissions to MEMBER
-- ============================================================
INSERT INTO role_permissions (role_id, permission_id, is_deleted, is_active)
SELECT r.id, p.id, 0, TRUE
FROM roles r, permissions p
WHERE r.name = 'MEMBER'
  AND p.name IN ('prayer_time:read', 'announcement:read', 'sunnah:read',
                 'donation:read');

-- ============================================================
-- Create a sample masjid
-- ============================================================
INSERT INTO masjids (name, address, pincode, city, state, country, phone, email, is_deleted, is_active)
VALUES ('Noor Masjid', '123 Main Street, Near Central Park', '110025', 'Delhi', 'Delhi', 'India',
        '+91-9876543210', 'info@noormasjid.com', 0, TRUE);

-- ============================================================
-- Create default 5 prayer times for the sample masjid
-- ============================================================
INSERT INTO prayer_times (masjid_id, prayer_name, azaan_time, prayer_time, sort_order, is_deleted, is_active)
VALUES
(1, 'Fajr', '05:00', '05:15', 0, 0, TRUE),
(1, 'Dhuhr', '12:15', '12:30', 1, 0, TRUE),
(1, 'Asr', '15:30', '15:45', 2, 0, TRUE),
(1, 'Maghrib', '18:15', '18:20', 3, 0, TRUE),
(1, 'Isha', '19:30', '19:45', 4, 0, TRUE);
