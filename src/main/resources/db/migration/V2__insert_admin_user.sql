INSERT INTO users (name, email, password, phone_number, address)
VALUES ('Usuário Teste', 'teste@admin.com', '$2a$10$OY0BncQQNK4pZruy1hz0/u8xwt.Nbr6xqzDycwGePywUv.4XBo9nC', '123456789',
        'Rua Teste');

INSERT INTO user_roles (user_id, role)
VALUES ((SELECT id FROM users WHERE email = 'teste@admin.com'), 'ADMIN');