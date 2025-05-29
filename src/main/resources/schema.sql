CREATE TABLE TB_ADDRESS
(
    id_address    INT AUTO_INCREMENT PRIMARY KEY,
    street        VARCHAR(255),
    number        VARCHAR(10),
    complement    VARCHAR(100),
    city          VARCHAR(100),
    neighbourhood VARCHAR(100),
    state         VARCHAR(50),
    zip_code      VARCHAR(20),
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    is_deleted    BOOLEAN
);

CREATE TABLE TB_CUSTOMER_USER
(
    id_customer_user       INT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(255),
    email             VARCHAR(255),
    password          VARCHAR(255),
    cpf               VARCHAR(14),
    phone             VARCHAR(20),
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP,
    is_deleted        BOOLEAN,
    profile_image_url VARCHAR(500)
);

CREATE TABLE TB_RESTAURANT
(
    id_restaurant  INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255),
    logo_image_url VARCHAR(500),
    fk_address     INT,
    phone          VARCHAR(20),
    whatsapp_phone VARCHAR(20),
    cnpj           VARCHAR(18),
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP,
    is_deleted     BOOLEAN,
    delivery_radius DOUBLE,
    FOREIGN KEY (fk_address) REFERENCES TB_ADDRESS (id_address)
);

CREATE TABLE TB_RESTAURANT_USER
(
    id_restaurant_user          INT AUTO_INCREMENT PRIMARY KEY,
    role              VARCHAR (8) CHECK (role IN ('ADMIN', 'MANAGER', 'EMPLOYEE')),
    name              VARCHAR(255),
    email             VARCHAR(255),
    password          VARCHAR(255),
    cpf               VARCHAR(14),
    phone             VARCHAR(20),
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP,
    is_deleted        BOOLEAN,
    profile_image_url VARCHAR(500),
    fk_restaurant     INT,
    FOREIGN KEY (fk_restaurant) REFERENCES TB_RESTAURANT (id_restaurant)
);

CREATE TABLE TB_CUSTOMER_ADDRESS
(
    id_customer_address INT AUTO_INCREMENT PRIMARY KEY,
    fk_customer         INT,
    fk_address          INT,
    nickname            VARCHAR(100),
    is_default          BOOLEAN,
    created_at          TIMESTAMP,
    updated_at          TIMESTAMP,
    is_deleted          BOOLEAN,
    FOREIGN KEY (fk_customer) REFERENCES TB_CUSTOMER (id_customer_user),
    FOREIGN KEY (fk_address) REFERENCES TB_ADDRESS (id_address)
);