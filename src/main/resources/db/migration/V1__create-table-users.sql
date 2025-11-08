CREATE TABLE expedix.users
(
    id            SERIAL PRIMARY KEY,
    personal_code VARCHAR(20) UNIQUE,
    full_name     VARCHAR(100) NOT NULL,
    phone         VARCHAR(20)  not null unique,
    password      varchar(80)  not null,
    is_deleted    boolean      not null default false,
    email         VARCHAR(100)
);

create table expedix.settlements
(
    id      SERIAL PRIMARY KEY,
    name    varchar(100) not null,
    user_id int          references expedix.users (id) on delete set null
);

create table expedix.shops
(
    id            SERIAL PRIMARY KEY,
    name          varchar(100) not null,
    address       varchar(100) not null,
    is_deleted    boolean      not null default false,
    settlement_id int references expedix.settlements (id)
);


INSERT INTO expedix.users (personal_code, full_name, phone, password, is_deleted, email)
VALUES ('1234567890', 'Ivan Cebam', '+37320153456', 'password123', false, 'ivanceban@example.com'),
       ('0987654321', 'Maria Popa', '+37320234567', 'password456', false, 'mariapopa@example.com'),
       ('1122334455', 'Alexandru Rusu', '+37320345678', 'password789', false, 'alexrusu@example.com');
INSERT INTO expedix.settlements (name, user_id)
VALUES ('Chisinau', 1),
       ('Balti', 1),
       ('Cahul', 2),
       ('Tiraspol', 3),
       ('Orhei', NULL);


INSERT INTO expedix.shops (name, address, is_deleted, settlement_id)
VALUES ('Shop Central', 'Str. Stefan cel Mare 1', false, 1),
       ('Market Nord', 'Str. Independenței 15', false, 1),
       ('Balti Store', 'Str. Victoriei 7', false, 2),
       ('Cahul Market', 'Str. Stefan cel Mare 10', false, 3),
       ('Tiraspol Shop', 'Str. Lenin 5', false, 4),
       ('Orhei MiniShop', 'Str. Republicii 3', false, 5);


CREATE TYPE order_status AS ENUM ('NEW', 'APPROVED', 'DELIVERED', 'CANCELLED');


create table expedix.orders
(
    id            bigserial primary key,
    user_id       int references users (id)       not null,
    settlement_id int references settlements (id) not null,
    shop_id       int references shops (id)       not null,
    created       timestamp    default current_timestamp,
    order_status  order_status default 'NEW'      not null,
    comment       text
)