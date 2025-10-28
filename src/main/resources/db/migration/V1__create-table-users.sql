CREATE TABLE expedix.users
(
    id            SERIAL PRIMARY KEY,
    personal_code VARCHAR(20)  NOT NULL UNIQUE,
    full_name     VARCHAR(100) NOT NULL,
    phone         VARCHAR(20),
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
    settlement_id int references expedix.settlements(id)
);
