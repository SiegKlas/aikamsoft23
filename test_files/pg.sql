CREATE TABLE Customer
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

CREATE TABLE Product
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(255),
    price DECIMAL(10, 2)
);

CREATE TABLE Purchase
(
    id            SERIAL PRIMARY KEY,
    customer_id   INT,
    product_id    INT,
    purchase_date DATE,
    FOREIGN KEY (customer_id) REFERENCES Customer (id),
    FOREIGN KEY (product_id) REFERENCES Product (id)
);

INSERT INTO Customer (first_name, last_name)
VALUES ('Иван', 'Иванов'),
       ('Петр', 'Петров'),
       ('Сидр', 'Сидоров'),
       ('Абоба', 'Абобов');

INSERT INTO Product (name, price)
VALUES ('Минеральная вода', 1.50),
       ('Сок', 2.50),
       ('Хлеб', 0.50);

INSERT INTO Purchase (customer_id, product_id, purchase_date)
VALUES (1, 1, CURRENT_DATE),
       (1, 2, CURRENT_DATE),
       (2, 1, CURRENT_DATE),
       (2, 2, CURRENT_DATE),
       (2, 2, '2021-03-22'),
       (2, 2, '2021-03-22'),
       (2, 2, '2022-03-22'),
       (2, 3, CURRENT_DATE),
       (3, 3, CURRENT_DATE),
       (4, 3, CURRENT_DATE);