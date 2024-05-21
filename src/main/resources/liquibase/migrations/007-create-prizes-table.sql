create table prizes
(
    amount      integer not null,
    id          serial
        primary key,
    description varchar(255),
    name        varchar(255),
    prize_type  varchar(255)
        constraint prizes_prize_type_check
            check ((prize_type)::text = ANY
        (ARRAY [('MINUTES'::character varying)::text, ('INTERNET'::character varying)::text, ('APP'::character varying)::text]))
    );

alter table prizes
    owner to postgres;