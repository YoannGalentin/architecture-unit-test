create table if not exists todo
(
    id varchar(255) not null,
    title varchar(255) not null,
    completed boolean not null
);
