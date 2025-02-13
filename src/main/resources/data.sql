CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255),
    address VARCHAR(255)
    );

-- (Opcional) Inserir alguns dados iniciais
INSERT INTO users (name, email, password, phone_number, address) VALUES
('John Doe', 'john.doe@example.com', 'hashed_password', '123-456-7890', '123 Main St'),
('Jane Doe', 'jane.doe@example.com', 'another_hashed_password', '987-654-3210', '456 Oak Ave');