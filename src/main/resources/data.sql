-- =====================================================
-- DADOS BÁSICOS - EATZ DATABASE (POSTGRESQL)
-- =====================================================

-- Inserir endereços básicos
INSERT INTO TB_ADDRESS (street, number, complement, city, neighbourhood, state, zip_code, created_at, updated_at, is_deleted) VALUES
('Rua das Flores', '123', 'Apto 45', 'São Paulo', 'Vila Madalena', 'SP', '05435-000', '2024-01-15 10:30:00', '2024-01-15 10:30:00', false),
('Avenida Paulista', '1000', 'Conjunto 12', 'São Paulo', 'Bela Vista', 'SP', '01310-100', '2024-01-16 14:20:00', '2024-01-16 14:20:00', false),
('Rua Augusta', '500', null, 'São Paulo', 'Consolação', 'SP', '01305-000', '2024-01-17 09:15:00', '2024-01-17 09:15:00', false),
('Rua Oscar Freire', '800', 'Loja 5', 'São Paulo', 'Jardins', 'SP', '01426-001', '2024-01-18 11:45:00', '2024-01-18 11:45:00', false),
('Avenida Faria Lima', '2500', 'Sala 1001', 'São Paulo', 'Itaim Bibi', 'SP', '01452-000', '2024-01-19 16:30:00', '2024-01-19 16:30:00', false);

-- Inserir restaurantes básicos
INSERT INTO TB_RESTAURANT (name, logo_image_url, fk_address, phone, whatsapp_phone, cnpj, created_at, updated_at, is_deleted, delivery_radius) VALUES
('Pizzaria Bella Napoli', 'https://example.com/logos/bella-napoli.jpg', 1, '(11) 3456-7890', '(11) 99456-7890', '12.345.678/0001-90', '2024-01-15 10:30:00', '2024-01-15 10:30:00', false, 5.0),
('Burger House Premium', 'https://example.com/logos/burger-house.jpg', 2, '(11) 9876-5432', '(11) 99876-5432', '23.456.789/0001-01', '2024-01-16 14:20:00', '2024-01-16 14:20:00', false, 8.0),
('Sushi Zen Master', 'https://example.com/logos/sushi-zen.jpg', 3, '(11) 2345-6789', '(11) 92345-6789', '34.567.890/0001-12', '2024-01-17 09:15:00', '2024-01-17 09:15:00', false, 6.0);

-- Inserir usuários clientes básicos
INSERT INTO TB_CUSTOMER_USER (name, email, password, cpf, phone, created_at, updated_at, is_deleted, profile_image_url) VALUES
('João Silva Santos', 'joao.silva@email.com', '$2a$10$9bIcjxlSEUdcammR4jgtjusvw0VETveHfBgtjVYIfYabJZsGN4umy', '123.456.789-00', '(11) 99999-1234', '2024-01-15 10:30:00', '2024-01-15 10:30:00', false, null),
('Maria Santos Oliveira', 'maria.santos@email.com', '$2a$10$9bIcjxlSEUdcammR4jgtjusvw0VETveHfBgtjVYIfYabJZsGN4umy', '987.654.321-00', '(11) 88888-5678', '2024-01-16 14:20:00', '2024-01-16 14:20:00', false, null),
('Pedro Costa Lima', 'pedro.costa@email.com', '$2a$10$9bIcjxlSEUdcammR4jgtjusvw0VETveHfBgtjVYIfYabJZsGN4umy', '456.789.123-00', '(11) 77777-9012', '2024-01-17 09:15:00', '2024-01-17 09:15:00', false, null);

-- Inserir usuários de restaurante básicos
INSERT INTO TB_RESTAURANT_USER (role, name, email, password, cpf, phone, created_at, updated_at, is_deleted, profile_image_url, fk_restaurant) VALUES
('ADMIN', 'Giuseppe Rossi', 'giuseppe@bellanapoli.com', '$2a$10$9bIcjxlSEUdcammR4jgtjusvw0VETveHfBgtjVYIfYabJZsGN4umy', '111.222.333-44', '(11) 3456-7890', '2024-01-15 10:30:00', '2024-01-15 10:30:00', false, null, 1),
('ADMIN', 'Carlos Burger', 'carlos@burgerhouse.com', '$2a$10$9bIcjxlSEUdcammR4jgtjusvw0VETveHfBgtjVYIfYabJZsGN4umy', '444.555.666-77', '(11) 9876-5432', '2024-01-16 14:20:00', '2024-01-16 14:20:00', false, null, 2),
('ADMIN', 'Takeshi Yamamoto', 'takeshi@sushizen.com', '$2a$10$9bIcjxlSEUdcammR4jgtjusvw0VETveHfBgtjVYIfYabJZsGN4umy', '666.777.888-99', '(11) 2345-6789', '2024-01-17 09:15:00', '2024-01-17 09:15:00', false, null, 3);

-- Associar endereços aos clientes
INSERT INTO TB_CUSTOMER_ADDRESS (fk_customer, fk_address, nickname, is_default, created_at, updated_at, is_deleted) VALUES
(1, 1, 'Casa', true, '2024-01-15 10:30:00', '2024-01-15 10:30:00', false),
(2, 2, 'Casa', true, '2024-01-16 14:20:00', '2024-01-16 14:20:00', false),
(3, 3, 'Casa', true, '2024-01-17 09:15:00', '2024-01-17 09:15:00', false);

-- Inserir itens básicos do menu
INSERT INTO TB_MENU_ITEM (name, description, price, only_local_consumption, photo_url, fk_restaurant, created_at, updated_at, is_deleted) VALUES
('Pizza Margherita', 'Pizza clássica com molho de tomate, mussarela e manjericão', 45.90, false, null, 1, '2024-01-15 10:30:00', '2024-01-15 10:30:00', false),
('Pizza Pepperoni', 'Pizza com molho de tomate, mussarela e pepperoni', 52.90, false, null, 1, '2024-01-15 10:35:00', '2024-01-15 10:35:00', false),
('Classic Burger', 'Hambúrguer artesanal 180g com alface, tomate e molho especial', 32.90, false, null, 2, '2024-01-16 14:20:00', '2024-01-16 14:20:00', false),
('Bacon Cheeseburger', 'Hambúrguer 180g com queijo cheddar e bacon crocante', 38.90, false, null, 2, '2024-01-16 14:25:00', '2024-01-16 14:25:00', false),
('Sushi Combo', 'Combinado de sushi com 12 peças variadas', 65.90, false, null, 3, '2024-01-17 09:15:00', '2024-01-17 09:15:00', false),
('Temaki Salmão', 'Temaki de salmão com cream cheese', 18.90, false, null, 3, '2024-01-17 09:20:00', '2024-01-17 09:20:00', false);