CREATE TABLE expedix.users
(
    id            SERIAL PRIMARY KEY,
    personal_code VARCHAR(20)  UNIQUE,
    full_name     VARCHAR(100) NOT NULL,
    phone         VARCHAR(20)  not null unique,
    password      varchar(80)  not null,
    email         VARCHAR(100)
);
create table expedix.settlements
(
    id      SERIAL PRIMARY KEY,
    name    varchar(100) not null,
    user_id int references expedix.users (id)
);
create table expedix.shops
(
    id            SERIAL PRIMARY KEY,
    name          varchar(100) not null,
    address       varchar(100) not null,
    is_deleted    boolean      not null default false,
    settlement_id int references expedix.settlements (id)
);
