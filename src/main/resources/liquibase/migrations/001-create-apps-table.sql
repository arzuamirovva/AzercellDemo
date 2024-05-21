create table apps
(
    id         serial
        primary key,
    price      double precision,
    start_date date,
    name       varchar(255)
);

alter table apps
    owner to postgres;