INSERT INTO TB_ADDRESS (id_address, street, number, complement, city, neighbourhood, state, zip_code, created_at, updated_at, is_deleted) VALUES
(10900, 'Rua das Flores', '123', 'Apto 45', 'São Paulo', 'Vila Madalena', 'SP', '05435-000', '2024-01-15 10:30:00', '2024-01-15 10:30:00', false);

INSERT INTO TB_RESTAURANT (id_restaurant, name, logo_image_url, fk_address, phone, whatsapp_phone, cnpj, created_at, updated_at, is_deleted, delivery_radius) VALUES
(10900, 'Pizzaria Bella Napoli', 'https://example.com/logos/bella-napoli.jpg', 10900, '(11) 3456-7890', '(11) 99456-7890', '12.345.678/0001-90', '2024-01-15 10:30:00', '2024-01-15 10:30:00', false, 5.0);

INSERT INTO TB_RESTAURANT_USER (id_restaurant_user, role, name, email, password, cpf, phone, created_at, updated_at, is_deleted, profile_image_url, fk_restaurant) VALUES
(10900, 'ADMIN', 'Natalia Rossi', 'natalia@bellanapoli.com', '$2a$10$BpZr7WgL/4cdvBkaSKyp0O8mLlPFd/BcmWVsFHKtHUANRS88OiPyu', '111.222.333-44', '(11) 3456-7890', '2024-01-15 10:30:00', '2024-01-15 10:30:00', false, null, 10900),
(10901, 'EMPLOYEE', 'Carlos Burger', 'carlos@burgerhouse.com', '$2a$10$BgZcFnVCzxO2Fcalom3OyetNQ1KX5lC48goghbFvr29MxkLm1cfCm', '444.555.666-77', '(11) 9876-5432', '2024-01-16 14:20:00', '2024-01-16 14:20:00', false, null, 2),
(10902, 'ADMIN', 'Takeshi Yamamoto', 'takeshi@sushizen.com', '$2a$10$BgZcFnVCzxO2Fcalom3OyetNQ1KX5lC48goghbFvr29MxkLm1cfCm', '666.777.888-99', '(11) 2345-6789', '2024-01-17 09:15:00', '2024-01-17 09:15:00', false, null, 3);

INSERT INTO TB_CUSTOMER_USER (id_customer_user, name, email, password, cpf, phone, created_at, updated_at, is_deleted, profile_image_url) VALUES
(10900, 'Larissa Silva Santos', 'larissa.silva@email.com', '$2a$10$BpZr7WgL/4cdvBkaSKyp0O8mLlPFd/BcmWVsFHKtHUANRS88OiPyu', '123.456.789-00', '(11) 99999-1234', '2024-01-15 10:30:00', '2024-01-15 10:30:00', false, null),
(10901, 'Mariana Santos Oliveira', 'mariana.santos@email.com', '$2a$10$9bIcjxlSEUdcammR4jgtjusvw0VETveHfBgtjVYIfYabJZsGN4umy', '987.654.321-00', '(11) 88888-5678', '2024-01-16 14:20:00', '2024-01-16 14:20:00', false, null),
(10902, 'Gabriel Costa Lima', 'gabriel.costa@email.com', '$2a$10$9bIcjxlSEUdcammR4jgtjusvw0VETveHfBgtjVYIfYabJZsGN4umy', '456.789.123-00', '(11) 77777-9012', '2024-01-17 09:15:00', '2024-01-17 09:15:00', false, null);

INSERT INTO TB_CUSTOMER_ADDRESS (fk_customer, fk_address, nickname, is_default, created_at, updated_at, is_deleted) VALUES
(10901, 10900, 'Casa', true, '2024-01-15 10:30:00', '2024-01-15 10:30:00', false);

INSERT INTO TB_MENU_ITEM (id_menu_item, name, description, price, only_local_consumption, photo_url, fk_restaurant, created_at, updated_at, is_deleted) VALUES
(10900, 'Pizza Margherita', 'Pizza com molho de tomate e mussarela', 45.90, false, null, 10900, '2024-01-15 10:30:00', '2024-01-15 10:30:00', false);