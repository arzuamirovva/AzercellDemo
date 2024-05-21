create table users
(
    id            serial
        primary key,
    passport_id   integer
        unique
        constraint fkihi622qbxfdemg98ia2cig3vw
            references passport,
    register_date date,
    email         varchar(255),
    full_name     varchar(255),
    password      varchar(255),
    role          varchar(255)
        constraint users_role_check
            check ((role)::text = ANY
        (ARRAY [('ADMIN'::character varying)::text, ('CUSTOMER'::character varying)::text]))
    );

alter table users
    owner to postgres;