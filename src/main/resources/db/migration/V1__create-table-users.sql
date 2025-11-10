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


CREATE TYPE expedix.type_of_unit AS ENUM ('PCS', 'UPC', 'BOX', 'NOT_SPECIFIED');
create table expedix.products
(
    id                bigserial primary key,
    name              varchar(255) not null,
    brand             varchar(100) not null,
    quantity_in_stock int                   default 0,
    unit_price        decimal(10, 2),
    type_of_unit      type_of_unit not null default 'NOT_SPECIFIED'
);
insert into expedix.products (name, brand, quantity_in_stock, unit_price, type_of_unit)
values ('Coca-Cola 0.5L', 'Coca-Cola', 100, 15.00, 'PCS'),
       ('Pepsi 0.5L', 'Pepsi', 80, 14.50, 'PCS'),
       ('Fanta 0.5L', 'Coca-Cola', 70, 14.00, 'PCS'),
       ('Snickers', 'Mars', 200, 10.50, 'PCS'),
       ('Milka Chocolate 100g', 'Milka', 150, 12.00, 'PCS'),
       ('Lays Chips 150g', 'PepsiCo', 120, 22.00, 'PCS'),
       ('Pringles Chips 160g', 'Kellogg', 90, 35.00, 'PCS'),
       ('BonAqua Water 1.5L', 'Coca-Cola', 200, 9.50, 'PCS'),
       ('Dobry Juice 1L', 'Dobry', 180, 18.00, 'PCS'),
       ('Milk 1L', 'Domik v derevne', 150, 12.50, 'PCS'),
       ('White Bread 500g', 'Borodinsky', 100, 8.00, 'PCS'),
       ('Whole Grain Bread 500g', 'Borodinsky', 80, 10.00, 'PCS'),
       ('Apples 1kg', 'FreshFarm', 120, 25.00, 'PCS'),
       ('Bananas 1kg', 'Tropical', 130, 28.00, 'PCS'),
       ('Oranges 1kg', 'CitrusLand', 90, 30.00, 'PCS'),
       ('Russian Cheese 200g', 'Karat', 100, 40.00, 'PCS'),
       ('Dutch Cheese 200g', 'President', 80, 55.00, 'PCS'),
       ('Eggs 10pcs', 'Selo Zelenoe', 150, 25.00, 'UPC'),
       ('Butter 200g', 'Vologodskoe', 90, 60.00, 'PCS'),
       ('Ground Coffee 250g', 'Jacobs', 70, 120.00, 'PCS'),
       ('Black Tea 100g', 'Ahmad Tea', 100, 75.00, 'PCS'),
       ('Oreo Cookies 150g', 'Mondelez', 120, 35.00, 'PCS'),
       ('Dumplings 1kg', 'Miritall', 80, 150.00, 'BOX'),
       ('Heinz Ketchup 500g', 'Heinz', 60, 85.00, 'PCS'),
       ('Mayonnaise 400g', 'Maheev', 90, 50.00, 'PCS'),
       ('Cheesecakes 500g', 'Homemade', 70, 80.00, 'PCS'),
       ('Cottage Cheese 200g', 'Prostokvashino', 100, 45.00, 'PCS'),
       ('Sausages 500g', 'Velcom', 80, 95.00, 'PCS'),
       ('Ice Cream 500g', 'Inmarko', 60, 120.00, 'PCS'),
       ('J7 Juice 1L', 'J7', 100, 35.00, 'PCS'),
       ('Green Tea 100g', 'Greenfield', 90, 80.00, 'PCS'),
       ('Rusks 250g', 'Borodinsky', 150, 12.00, 'PCS'),
       ('Flour 2kg', 'Makfa', 200, 45.00, 'UPC'),
       ('Rice 1kg', 'Rice Field', 180, 50.00, 'PCS'),
       ('Pasta 500g', 'Barilla', 120, 40.00, 'PCS'),
       ('Chicken Meat 1kg', 'Petelinka', 90, 250.00, 'PCS'),
       ('Beef 1kg', 'Farmer', 70, 450.00, 'PCS'),
       ('Pork 1kg', 'Farmer', 80, 350.00, 'PCS'),
       ('Frozen Fish 1kg', 'Mercury', 100, 300.00, 'PCS'),
       ('Chicken Wings 1kg', 'Petelinka', 80, 220.00, 'PCS'),
       ('Curd 100g', 'Chudo', 120, 20.00, 'PCS'),
       ('Drinkable Yogurt 290ml', 'Activia', 150, 25.00, 'PCS'),
       ('Tomato Paste 500g', 'Pomidorka', 100, 60.00, 'PCS'),
       ('Cucumbers 1kg', 'Farmer', 80, 45.00, 'PCS'),
       ('Tomatoes 1kg', 'Farmer', 90, 55.00, 'PCS'),
       ('Onions 1kg', 'Farmer', 120, 20.00, 'PCS'),
       ('Potatoes 1kg', 'Farmer', 150, 15.00, 'PCS'),
       ('Carrots 1kg', 'Farmer', 130, 18.00, 'PCS'),
       ('Garlic 1kg', 'Farmer', 100, 80.00, 'PCS'),
       ('Lemons 1kg', 'CitrusLand', 90, 70.00, 'PCS');

CREATE TABLE expedix.order_item
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    order_id    BIGINT NOT NULL REFERENCES expedix.orders (id) ON DELETE CASCADE,
    product_id  BIGINT NOT NULL REFERENCES expedix.products (id),
    quantity    INT NOT NULL DEFAULT 1 CHECK (quantity > 0),
    unit_price  DECIMAL(10,2) NOT NULL CHECK (unit_price >= 0),
    total_price DECIMAL(10,2) NOT NULL CHECK (total_price >= 0),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES expedix.orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES expedix.products(id)
);

CREATE INDEX idx_order_item_order_id ON expedix.order_item (order_id);
CREATE INDEX idx_order_item_product_id ON expedix.order_item (product_id);
CREATE INDEX idx_order_item_created_at ON expedix.order_item (created_at);