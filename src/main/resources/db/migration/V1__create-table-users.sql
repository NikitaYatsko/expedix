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
    created       timestamp      default current_timestamp,
    order_status  order_status   default 'NEW'    not null,
    total_price   decimal(10, 2) default 0,
    comment       text
);


create TYPE type_of_unit as enum ('ШТ.','УПК.','КОРОБКА.','НЕ УКАЗАНО');
create table expedix.products
(
    id                bigserial primary key,
    name              varchar(255) not null,
    brand             varchar(100) not null,
    quantity_in_stock int                   default 0,
    unit_price        decimal(10, 2),
    type_of_unit      type_of_unit not null default 'НЕ УКАЗАНО'
);
insert into expedix.products (name, brand, quantity_in_stock, unit_price, type_of_unit)
values ('Кока-Кола 0.5л', 'Coca-Cola', 100, 15.00, 'ШТ.'),
       ('Пепси 0.5л', 'Pepsi', 80, 14.50, 'ШТ.'),
       ('Фанта 0.5л', 'Coca-Cola', 70, 14.00, 'ШТ.'),
       ('Сникерс', 'Mars', 200, 10.50, 'ШТ.'),
       ('Милка шоколад 100г', 'Milka', 150, 12.00, 'ШТ.'),
       ('Чипсы Lays 150г', 'PepsiCo', 120, 22.00, 'ШТ.'),
       ('Чипсы Pringles 160г', 'Kellogg', 90, 35.00, 'ШТ.'),
       ('Вода BonAqua 1.5л', 'Coca-Cola', 200, 9.50, 'ШТ.'),
       ('Сок Добрый 1л', 'Добрый', 180, 18.00, 'ШТ.'),
       ('Молоко 1л', 'Домик в деревне', 150, 12.50, 'ШТ.'),
       ('Хлеб белый 500г', 'Бородинский', 100, 8.00, 'ШТ.'),
       ('Хлеб цельнозерновой 500г', 'Бородинский', 80, 10.00, 'ШТ.'),
       ('Яблоки 1кг', 'FreshFarm', 120, 25.00, 'ШТ.'),
       ('Бананы 1кг', 'Tropical', 130, 28.00, 'ШТ.'),
       ('Апельсины 1кг', 'CitrusLand', 90, 30.00, 'ШТ.'),
       ('Сыр Российский 200г', 'Карат', 100, 40.00, 'ШТ.'),
       ('Сыр Голландский 200г', 'President', 80, 55.00, 'ШТ.'),
       ('Яйца 10шт', 'Село Зеленое', 150, 25.00, 'УПК.'),
       ('Масло сливочное 200г', 'Вологодское', 90, 60.00, 'ШТ.'),
       ('Кофе молотый 250г', 'Jacobs', 70, 120.00, 'ШТ.'),
       ('Чай черный 100г', 'Ahmad Tea', 100, 75.00, 'ШТ.'),
       ('Печенье Орео 150г', 'Mondelez', 120, 35.00, 'ШТ.'),
       ('Пельмени 1кг', 'Мириталь', 80, 150.00, 'ШТ.'),
       ('Кетчуп Heinz 500г', 'Heinz', 60, 85.00, 'ШТ.'),
       ('Майонез 400г', 'Махеевъ', 90, 50.00, 'ШТ.'),
       ('Сырники 500г', 'Домашние', 70, 80.00, 'ШТ.'),
       ('Творог 200г', 'Простоквашино', 100, 45.00, 'ШТ.'),
       ('Сосиски 500г', 'Велком', 80, 95.00, 'ШТ.'),
       ('Мороженое пломбир 500г', 'Инмарко', 60, 120.00, 'ШТ.'),
       ('Сок J7 1л', 'J7', 100, 35.00, 'ШТ.'),
       ('Чай зеленый 100г', 'Greenfield', 90, 80.00, 'ШТ.'),
       ('Сухари 250г', 'Бородинский', 150, 12.00, 'ШТ.'),
       ('Мука 2кг', 'Макфа', 200, 45.00, 'УПК.'),
       ('Рис 1кг', 'Рисовое поле', 180, 50.00, 'ШТ.'),
       ('Макароны 500г', 'Barilla', 120, 40.00, 'ШТ.'),
       ('Мясо куриное 1кг', 'Петелинка', 90, 250.00, 'ШТ.'),
       ('Говядина 1кг', 'Фермер', 70, 450.00, 'ШТ.'),
       ('Свинина 1кг', 'Фермер', 80, 350.00, 'ШТ.'),
       ('Рыба замороженная 1кг', 'Меркурий', 100, 300.00, 'ШТ.'),
       ('Куриные крылышки 1кг', 'Петелинка', 80, 220.00, 'ШТ.'),
       ('Творожок 100г', 'Чудо', 120, 20.00, 'ШТ.'),
       ('Йогурт питьевой 290мл', 'Активиа', 150, 25.00, 'ШТ.'),
       ('Паста томатная 500г', 'Помидорка', 100, 60.00, 'ШТ.'),
       ('Огурцы 1кг', 'Фермер', 80, 45.00, 'ШТ.'),
       ('Помидоры 1кг', 'Фермер', 90, 55.00, 'ШТ.'),
       ('Лук репчатый 1кг', 'Фермер', 120, 20.00, 'ШТ.'),
       ('Картофель 1кг', 'Фермер', 150, 15.00, 'ШТ.'),
       ('Морковь 1кг', 'Фермер', 130, 18.00, 'ШТ.'),
       ('Чеснок 1кг', 'Фермер', 100, 80.00, 'ШТ.'),
       ('Лимоны 1кг', 'CitrusLand', 90, 70.00, 'ШТ.');


create table expedix.order_products
(
    order_id   bigint references expedix.orders (id) on delete cascade,
    product_id bigint references expedix.products (id) on delete cascade,
    quantity   int not null check (quantity > 0),
    primary key (order_id, product_id)
);
