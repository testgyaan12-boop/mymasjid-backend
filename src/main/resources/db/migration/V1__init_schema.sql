-- ============================================================
-- Noor Al Masjid - Initial Schema
-- All tables include BaseEntity columns:
--   id BIGSERIAL PK, is_deleted INT DEFAULT 0, remarks VARCHAR(150),
--   created_at DATE DEFAULT CURRENT_DATE, created_by BIGINT,
--   updated_at TIMESTAMP, updated_by BIGINT, is_active BOOLEAN
-- ============================================================

-- ============================================================
-- CORE TABLES
-- ============================================================

CREATE TABLE masjids (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    name VARCHAR(200) NOT NULL,
    address VARCHAR(500),
    pincode VARCHAR(10) NOT NULL,
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(150),
    logo VARCHAR(500),
    website VARCHAR(500)
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    avatar VARCHAR(500),
    system_role VARCHAR(20) NOT NULL DEFAULT 'USER',
    current_masjid_id BIGINT REFERENCES masjids(id)
);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    module VARCHAR(50)
);

CREATE TABLE role_permissions (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    role_id BIGINT NOT NULL REFERENCES roles(id),
    permission_id BIGINT NOT NULL REFERENCES permissions(id),
    UNIQUE(role_id, permission_id)
);

CREATE TABLE user_masjid_roles (
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
    role_id BIGINT NOT NULL REFERENCES roles(id),
    UNIQUE(user_id, masjid_id)
);

-- ============================================================
-- CMS TABLES
-- ============================================================

CREATE TABLE branding (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    masjid_name VARCHAR(200),
    primary_color VARCHAR(20),
    secondary_color VARCHAR(20),
    logo VARCHAR(500)
);

CREATE TABLE home_announcements (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    status VARCHAR(100),
    title VARCHAR(255),
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    image VARCHAR(500)
);

CREATE TABLE prayer_times (
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
    azaan_time VARCHAR(10),
    prayer_time VARCHAR(10),
    hour INT,
    minute INT,
    icon VARCHAR(50),
    sort_order INT
);

CREATE TABLE jumuah_config (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    prayer_time VARCHAR(10),
    azaan_time VARCHAR(10)
);

CREATE TABLE ramadan_config (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    taraweeh VARCHAR(100),
    note TEXT,
    iftar_message TEXT,
    fitra_rate DOUBLE PRECISION
);

CREATE TABLE janazahs (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    title VARCHAR(255) NOT NULL,
    time VARCHAR(50),
    location VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    event_date TIMESTAMP
);

CREATE TABLE gumshudas (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    title VARCHAR(255) NOT NULL,
    details TEXT,
    contact VARCHAR(100),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    image VARCHAR(500),
    found BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE general_announcements (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    icon VARCHAR(50)
);

CREATE TABLE donation_causes (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    upi VARCHAR(100),
    badge VARCHAR(100),
    qr_image VARCHAR(500)
);

CREATE TABLE monthly_donations (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    month VARCHAR(20),
    amount DOUBLE PRECISION,
    status VARCHAR(20)
);

CREATE TABLE expenses (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    label VARCHAR(255),
    value DOUBLE PRECISION
);

CREATE TABLE about_services (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    icon VARCHAR(100)
);

CREATE TABLE team_members (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    name VARCHAR(100) NOT NULL,
    role VARCHAR(100),
    email VARCHAR(150),
    mobile VARCHAR(20),
    image VARCHAR(500),
    responsibilities TEXT
);

-- ============================================================
-- SUNNAH TABLES
-- ============================================================

CREATE TABLE sunnahs (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    title VARCHAR(255) NOT NULL,
    arabic TEXT,
    transliteration TEXT,
    meaning TEXT,
    category VARCHAR(100)
);

CREATE TABLE active_sunnah_broadcasts (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    sunnah_id BIGINT NOT NULL REFERENCES sunnahs(id),
    broadcast_date DATE NOT NULL
);

CREATE TABLE user_saved_sunnahs (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    sunnah_id BIGINT NOT NULL REFERENCES sunnahs(id),
    UNIQUE(user_id, sunnah_id)
);

-- ============================================================
-- TASBIH TABLES
-- ============================================================

CREATE TABLE tasbih_logs (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    dhikr VARCHAR(255),
    count INT NOT NULL,
    session_date DATE NOT NULL
);

CREATE TABLE custom_adhkars (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    title VARCHAR(255) NOT NULL,
    arabic TEXT,
    transliteration TEXT,
    meaning TEXT,
    color VARCHAR(20)
);

-- ============================================================
-- DONATION TABLES
-- ============================================================

CREATE TABLE donations (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    masjid_id BIGINT NOT NULL REFERENCES masjids(id),
    user_id BIGINT REFERENCES users(id),
    amount DOUBLE PRECISION NOT NULL,
    currency VARCHAR(10) DEFAULT 'INR',
    transaction_id VARCHAR(255),
    donation_date TIMESTAMP,
    status VARCHAR(20)
);

-- ============================================================
-- ZAKAT TABLES
-- ============================================================

CREATE TABLE zakat_calculations (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    gold_value DOUBLE PRECISION,
    silver_value DOUBLE PRECISION,
    cash_value DOUBLE PRECISION,
    business_value DOUBLE PRECISION,
    property_value DOUBLE PRECISION,
    liabilities DOUBLE PRECISION,
    net_worth DOUBLE PRECISION,
    zakat_due DOUBLE PRECISION,
    zakat_type VARCHAR(50),
    calculation_date DATE NOT NULL
);

-- ============================================================
-- QURAN TABLES
-- ============================================================

CREATE TABLE surahs (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    surah_number INT NOT NULL UNIQUE,
    name_arabic VARCHAR(100),
    name_english VARCHAR(100),
    revelation_place VARCHAR(20),
    number_of_ayats INT
);

CREATE TABLE ayats (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    surah_id BIGINT NOT NULL REFERENCES surahs(id),
    ayat_number INT NOT NULL,
    text_arabic TEXT,
    text_translation TEXT,
    text_transliteration TEXT,
    audio_url VARCHAR(500)
);

CREATE TABLE reading_progress (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    completed_ayats TEXT,
    completed_juzs TEXT,
    last_read_surah INT,
    last_read_juz INT,
    last_read_page INT,
    last_read_mode VARCHAR(20),
    surah_checkpoints TEXT,
    juz_checkpoints TEXT,
    session_time BIGINT
);

-- ============================================================
-- CONTENT PAGES TABLE
-- ============================================================

CREATE TABLE content_pages (
    id BIGSERIAL PRIMARY KEY,
    is_deleted INT NOT NULL DEFAULT 0,
    remarks VARCHAR(150),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by BIGINT,
    updated_at TIMESTAMP,
    updated_by BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    body TEXT,
    content_type VARCHAR(50),
    summary_ai TEXT,
    author_id BIGINT REFERENCES users(id),
    published_at TIMESTAMP,
    is_draft BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_masjids_pincode ON masjids(pincode);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_user_masjid_roles_user ON user_masjid_roles(user_id);
CREATE INDEX idx_user_masjid_roles_masjid ON user_masjid_roles(masjid_id);
CREATE INDEX idx_role_permissions_role ON role_permissions(role_id);
CREATE INDEX idx_prayer_times_masjid ON prayer_times(masjid_id);
CREATE INDEX idx_janazahs_masjid ON janazahs(masjid_id);
CREATE INDEX idx_gumshudas_masjid ON gumshudas(masjid_id);
CREATE INDEX idx_announcements_masjid ON general_announcements(masjid_id);
CREATE INDEX idx_sunnahs_masjid ON sunnahs(masjid_id);
CREATE INDEX idx_donations_masjid ON donations(masjid_id);
CREATE INDEX idx_donations_user ON donations(user_id);
