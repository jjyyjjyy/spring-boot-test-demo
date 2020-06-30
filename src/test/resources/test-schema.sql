create table "user"
(
    id         bigserial not null primary key,
    username   varchar,
    gender     integer,
    address    varchar,
    created_at timestamp,
    updated_at timestamp
);
