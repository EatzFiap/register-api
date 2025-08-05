-- =====================================================
-- SCHEMA EATZ DATABASE - COMPATÍVEL COM POSTGRESQL
-- =====================================================
DROP TABLE IF EXISTS TB_MENU_ITEM CASCADE;
DROP TABLE IF EXISTS TB_CUSTOMER_ADDRESS CASCADE;
DROP TABLE IF EXISTS TB_RESTAURANT_USER CASCADE;
DROP TABLE IF EXISTS TB_RESTAURANT CASCADE;
DROP TABLE IF EXISTS TB_CUSTOMER_USER CASCADE;
DROP TABLE IF EXISTS TB_ADDRESS CASCADE;
DROP TABLE IF EXISTS TB_RESTAURANT_USER_TYPE CASCADE;

CREATE TABLE TB_ADDRESS
(
    id_address    SERIAL PRIMARY KEY,
    street        VARCHAR(255),
    number        VARCHAR(10),
    complement    VARCHAR(100),
    city          VARCHAR(100),
    neighbourhood VARCHAR(100),
    state         VARCHAR(50),
    zip_code      VARCHAR(20),
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    is_deleted    BOOLEAN DEFAULT FALSE
);

CREATE TABLE TB_CUSTOMER_USER
(
    id_customer_user  SERIAL PRIMARY KEY,
    name              VARCHAR(255),
    email             VARCHAR(255),
    password          VARCHAR(255),
    cpf               VARCHAR(14),
    phone             VARCHAR(20),
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP,
    is_deleted        BOOLEAN DEFAULT FALSE,
    profile_image_url VARCHAR(500)
);

CREATE TABLE TB_RESTAURANT
(
    id_restaurant   SERIAL PRIMARY KEY,
    name            VARCHAR(255),
    logo_image_url  VARCHAR(500),
    fk_address      INTEGER,
    phone           VARCHAR(20),
    whatsapp_phone  VARCHAR(20),
    cnpj            VARCHAR(18),
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP,
    is_deleted      BOOLEAN DEFAULT FALSE,
    delivery_radius DOUBLE PRECISION,
    FOREIGN KEY (fk_address) REFERENCES TB_ADDRESS (id_address)
);

CREATE TABLE TB_RESTAURANT_USER_TYPE
(
    id_restaurant_user_type SERIAL PRIMARY KEY,
    name                    VARCHAR(50) NOT NULL UNIQUE,
    description             VARCHAR(255),
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted              BOOLEAN DEFAULT FALSE
);

CREATE TABLE TB_RESTAURANT_USER
(
    id_restaurant_user SERIAL PRIMARY KEY,
    role               VARCHAR(8) CHECK (role IN ('ADMIN', 'MANAGER', 'EMPLOYEE')),
    name               VARCHAR(255),
    email              VARCHAR(255),
    password           VARCHAR(255),
    cpf                VARCHAR(14),
    phone              VARCHAR(20),
    created_at         TIMESTAMP,
    updated_at         TIMESTAMP,
    is_deleted         BOOLEAN DEFAULT FALSE,
    profile_image_url  VARCHAR(500),
    fk_restaurant      INTEGER,
    fk_restaurant_user_type INTEGER,
    FOREIGN KEY (fk_restaurant) REFERENCES TB_RESTAURANT (id_restaurant),
    FOREIGN KEY (fk_restaurant_user_type) REFERENCES TB_RESTAURANT_USER_TYPE (id_restaurant_user_type)
);

CREATE TABLE TB_CUSTOMER_ADDRESS
(
    id_customer_address SERIAL PRIMARY KEY,
    fk_customer         INTEGER,
    fk_address          INTEGER,
    nickname            VARCHAR(100),
    is_default          BOOLEAN DEFAULT FALSE,
    created_at          TIMESTAMP,
    updated_at          TIMESTAMP,
    is_deleted          BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (fk_customer) REFERENCES TB_CUSTOMER_USER (id_customer_user),
    FOREIGN KEY (fk_address) REFERENCES TB_ADDRESS (id_address)
);

CREATE TABLE TB_MENU_ITEM
(
    id_menu_item           BIGSERIAL PRIMARY KEY,
    name                   VARCHAR(255) NOT NULL,
    description            TEXT,
    price                  DECIMAL(10,2) NOT NULL,
    only_local_consumption BOOLEAN DEFAULT FALSE,
    photo_url              VARCHAR(500),
    fk_restaurant          INTEGER NOT NULL,
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted             BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (fk_restaurant) REFERENCES TB_RESTAURANT (id_restaurant)
);