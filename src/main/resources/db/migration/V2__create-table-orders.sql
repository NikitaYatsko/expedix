CREATE TYPE order_status AS ENUM ('NEW', 'APPROVED', 'DELIVERED', 'CANCELED');


create table expedix.orders
(
    id            bigserial primary key,
    user_id       int references users (id)       not null,
    settlement_id int references settlements (id) not null,
    shop_id       int references shops (id)       not null,
    address       varchar(255)                    not null,
    created       timestamp    default current_timestamp,
    order_status  order_status default 'NEW'      not null,
    comment       text
)