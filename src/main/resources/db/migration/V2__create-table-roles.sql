create table expedix.roles
(
    id               serial primary key not null,
    name             varchar(50)        not null,
    user_system_role varchar(64)        not null,
    active           boolean            not null default true
);
create table user_roles
(
    user_id bigint references users (id) on delete cascade,
    role_id integer references roles (id) on delete cascade,
    primary key (user_id, role_id)
);