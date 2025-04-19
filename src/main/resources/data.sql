INSERT INTO TB_ADDRESS (street, number, complement, city, neighbourhood, state, zip_code)
VALUES
    ('Rua das Flores', '123', 'Apto 202', 'São Paulo', 'Jardins', 'SP', '01234-567'),
    ('Avenida Paulista', '456', '', 'São Paulo', 'Centro', 'SP', '01311-000'),
    ('Av. Brasil', '1000', '', 'Rio de Janeiro', 'Copacabana', 'RJ', '22040-002');

INSERT INTO TB_CUSTOMER (name, email, password, cpf, phone)
VALUES
    ('Maria Silva', 'maria@email.com', '$2a$10$iR6HsE.nl6fS6nPJ6SmajO9DubU/HDQg87C7GCpYm4zT18jKN43ji', '123.456.789-00', '(11) 91234-5678'),
    ('João Souza', 'joao@email.com', '$2a$10$iR6HsE.nl6fS6nPJ6SmajO9DubU/HDQg87C7GCpYm4zT18jKN43ji', '987.654.321-00', '(21) 99876-5432');

INSERT INTO TB_RESTAURANT (name, logo_image_url, fk_address, phone, whatsapp_phone, cnpj, delivery_radius)
VALUES
    ('Restaurante Bom Sabor', 'https://imgur.com/logo1.png', 3, '(11) 3456-7890', '(11) 91234-1111', '12.345.678/0001-99', 10.5);

INSERT INTO TB_ADMIN (name, email, password, cpf, phone, fk_restaurant)
VALUES
    ('Carlos Admin', 'admin@restaurante.com', 'adminpass', '321.654.987-00', '(11) 98888-8888', 1);

INSERT INTO TB_CUSTOMER_ADDRESS (fk_customer, fk_address, nickname, is_default)
VALUES
    (1, 1, 'Casa', TRUE),
    (2, 2, 'Trabalho', FALSE);
