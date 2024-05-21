create table passport
(
    id            serial
        primary key,
    birth_date    date,
    fin           varchar(255),
    gender        varchar(255)
        constraint passport_gender_check
            check ((gender)::text = ANY
        (ARRAY [('MALE'::character varying)::text, ('FEMALE'::character varying)::text])),
    name          varchar(255),
    serial_number varchar(255),
    surname       varchar(255)
);

alter table passport
    owner to postgres;