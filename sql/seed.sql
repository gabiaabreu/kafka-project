INSERT INTO customers (name, email, phone, cpf, address) VALUES
('João Silva', 'joao.silva@example.com', '123456789', '12345678901', 'Rua A, 123'),
('Maria Oliveira', 'maria.oliveira@example.com', '987654321', '98765432100', 'Avenida B, 456'),
('Carlos Souza', 'carlos.souza@example.com', '555555555', '55555555511', 'Travessa C, 789');

INSERT INTO products (name, description, price, stock_qty) VALUES
('Camiseta', 'Camiseta de algodão', 29.90, 100),
('Calça Jeans', 'Calça jeans azul', 89.90, 50),
('Tênis', 'Tênis esportivo', 199.90, 30);

INSERT INTO orders (customer_id, total_amount, payment_method, payment_status) VALUES
(1, 119.80, 'CREDIT_CARD', 'PENDING'),
(2, 29.90, 'DEBIT_CARD', 'PENDING'),
(3, 199.90, 'PIX', 'PENDING');

INSERT INTO order_products (order_id, product_id, quantity) VALUES
(1, 1, 2),
(1, 2, 1),
(2, 1, 1),
(3, 3, 1);
