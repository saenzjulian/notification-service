create table "notifications" (
    id          bigserial primary key,
    type        varchar(255) not null,
    username    varchar(255) not null,
    message     text not null,
    instant     timestamp default now()
);