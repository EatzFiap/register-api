CREATE TABLE TB_ADDRESS
(
    id_address    SERIAL PRIMARY KEY,
    street        VARCHAR(200),
    number        VARCHAR(10),
    complement    VARCHAR(200),
    city          VARCHAR(100),
    neighbourhood VARCHAR(100),
    state         VARCHAR(50),
    zip_code      VARCHAR(20),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted    BOOLEAN
);

CREATE TABLE TB_CUSTOMER
(
    id_customer       SERIAL PRIMARY KEY,
    name              VARCHAR(200),
    email             VARCHAR(200),
    password          VARCHAR(200),
    cpf               VARCHAR(20),
    phone             VARCHAR(20),
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted        BOOLEAN,
    profile_image_url VARCHAR(500)
);

CREATE TABLE TB_RESTAURANT
(
    id_restaurant   SERIAL PRIMARY KEY,
    name            VARCHAR(200),
    logo_image_url  VARCHAR(500),
    fk_address      INT,
    phone           VARCHAR(20),
    whatsapp_phone  VARCHAR(20),
    cnpj            VARCHAR(18),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted      BOOLEAN,
    delivery_radius NUMERIC(5, 2),
    FOREIGN KEY (fk_address) REFERENCES TB_ADDRESS (id_address)
);

CREATE TABLE TB_ADMIN
(
    id_admin          SERIAL PRIMARY KEY,
    name              VARCHAR(200),
    email             VARCHAR(200),
    password          VARCHAR(200),
    cpf               VARCHAR(14),
    phone             VARCHAR(20),
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted        BOOLEAN,
    profile_image_url VARCHAR(500),
    fk_restaurant     INT,
    FOREIGN KEY (fk_restaurant) REFERENCES TB_RESTAURANT (id_restaurant)
);

CREATE TABLE TB_CUSTOMER_ADDRESS
(
    id_customer_address SERIAL PRIMARY KEY,
    fk_customer         INT,
    fk_address          INT,
    nickname            VARCHAR(100),
    is_default          BOOLEAN   DEFAULT FALSE,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted          BOOLEAN,
    FOREIGN KEY (fk_customer) REFERENCES TB_CUSTOMER (id_customer),
    FOREIGN KEY (fk_address) REFERENCES TB_ADDRESS (id_address)
);

CREATE TABLE TB_CATEGORY
(
    id_category SERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted  BOOLEAN   DEFAULT FALSE,
    is_active   BOOLEAN   DEFAULT TRUE
);

CREATE TABLE TB_RESTAURANT_CATEGORIES
(
    id_restaurant_category SERIAL PRIMARY KEY,
    fk_restaurant          INT NOT NULL,
    fk_category            INT NOT NULL,
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted             BOOLEAN   DEFAULT FALSE,
    FOREIGN KEY (fk_restaurant) REFERENCES TB_RESTAURANT (id_restaurant),
    FOREIGN KEY (fk_category) REFERENCES TB_CATEGORY (id_category)
);

CREATE TABLE TB_FAVORITE_RESTAURANTS
(
    id_favorite_restaurant SERIAL PRIMARY KEY,
    fk_customer            INT NOT NULL,
    fk_restaurant          INT NOT NULL,
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted             BOOLEAN   DEFAULT FALSE,
    FOREIGN KEY (fk_customer) REFERENCES TB_CUSTOMER (id_customer),
    FOREIGN KEY (fk_restaurant) REFERENCES TB_RESTAURANT (id_restaurant)
);

CREATE TABLE TB_COUPON
(
    id_coupon      SERIAL PRIMARY KEY,
    type           VARCHAR(50)    NOT NULL,
    code           VARCHAR(100)   NOT NULL,
    discount       NUMERIC(10, 2) NOT NULL,
    discount_limit NUMERIC(10, 2),
    fk_restaurant  INT            NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted     BOOLEAN   DEFAULT FALSE,
    is_active      BOOLEAN   DEFAULT TRUE,
    remaining_uses INT       DEFAULT 0,
    FOREIGN KEY (fk_restaurant) REFERENCES TB_RESTAURANT (id_restaurant)
);

CREATE TABLE TB_ORDER
(
    id_order      SERIAL PRIMARY KEY,
    fk_user       INT            NOT NULL,
    status        VARCHAR(50)    NOT NULL,
    total         NUMERIC(10, 2) NOT NULL,
    delivered_at  TIMESTAMP,
    notes         TEXT,
    fk_address    INT            NOT NULL,
    fk_restaurant INT            NOT NULL,
    rating        NUMERIC(2, 1),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted    BOOLEAN   DEFAULT FALSE,
    fk_coupon     INT,
    FOREIGN KEY (fk_user) REFERENCES TB_CUSTOMER (id_customer),
    FOREIGN KEY (fk_address) REFERENCES TB_ADDRESS (id_address),
    FOREIGN KEY (fk_restaurant) REFERENCES TB_RESTAURANT (id_restaurant),
    FOREIGN KEY (fk_coupon) REFERENCES TB_COUPON (id_coupon)
);

CREATE TABLE TB_CLOSED_ORDER
(
    id_closed_order SERIAL PRIMARY KEY,
    fk_user         INT            NOT NULL,
    status          VARCHAR(50)    NOT NULL,
    total           NUMERIC(10, 2) NOT NULL,
    delivered_at    TIMESTAMP,
    notes           TEXT,
    fk_address      INT            NOT NULL,
    fk_restaurant   INT            NOT NULL,
    rating          NUMERIC(2, 1),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted      BOOLEAN   DEFAULT FALSE,
    fk_coupon       INT,
    FOREIGN KEY (fk_user) REFERENCES TB_CUSTOMER (id_customer),
    FOREIGN KEY (fk_address) REFERENCES TB_ADDRESS (id_address),
    FOREIGN KEY (fk_restaurant) REFERENCES TB_RESTAURANT (id_restaurant),
    FOREIGN KEY (fk_coupon) REFERENCES TB_COUPON (id_coupon)
);

CREATE TABLE TB_PRODUCT
(
    id_product    SERIAL PRIMARY KEY,
    name          VARCHAR(200)   NOT NULL,
    description   TEXT,
    price         NUMERIC(10, 2) NOT NULL,
    image_url     TEXT,
    fk_restaurant INT            NOT NULL,
    is_active     BOOLEAN   DEFAULT TRUE,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted    BOOLEAN   DEFAULT FALSE,
    FOREIGN KEY (fk_restaurant) REFERENCES TB_RESTAURANT (id_restaurant)
);

CREATE TABLE TB_PASSWORD_RESET_TOKEN
(
    id_password_reset SERIAL PRIMARY KEY,
    id_user           INT          NOT NULL,
    user_type         VARCHAR(50)  NOT NULL,
    token             VARCHAR(200) NOT NULL,
    expire_at         TIMESTAMP    NOT NULL,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE TB_ORDER_PRODUCTS
(
    id_order_product SERIAL PRIMARY KEY,
    fk_order         INT NOT NULL,
    fk_product       INT NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted       BOOLEAN   DEFAULT FALSE,
    FOREIGN KEY (fk_order) REFERENCES TB_ORDER (id_order),
    FOREIGN KEY (fk_product) REFERENCES TB_PRODUCT (id_product)
);
