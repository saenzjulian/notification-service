create table "rate_limit" (
    id          bigserial primary key,
    type        varchar(255) not null,
    username    varchar(255) not null,
    "count"     integer not null,
    updated_at  timestamp default now()
    -- primary key (type, username)
);