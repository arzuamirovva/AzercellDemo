create table your_own_tariff
(
    creator_number  integer
        unique
        constraint fkm30qfcg26tnqutmp6oyaehqqw
            references numbers,
    id              serial
        primary key,
    internet_amount double precision,
    minutes         integer,
    total_charge    double precision,
    valid_period    integer
);

alter table your_own_tariff
    owner to postgres;