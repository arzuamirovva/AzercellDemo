create table spins
(
    id        bigserial
        primary key,
    spin_date timestamp(6),
    user_id   bigint,
    prize     varchar(255)
);

alter table spins
    owner to postgres;