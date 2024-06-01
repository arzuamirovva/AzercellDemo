create table numbers
(
    id               serial
        primary key,
    assign_time      timestamp(6),
    balance          double precision,
    free_internet    double precision,
    free_minutes     integer,
    has_chance       boolean,
    has_credit       boolean,
    internet_balance double precision,
    last_spin_time   timestamp(6),
    minute_balance   integer,
    number           varchar(255),
    password         varchar(255),
    sms_balance      integer,
    status           varchar(255)
        constraint numbers_status_check
            check ((status)::text = ANY
        ((ARRAY ['ACTIVE'::character varying, 'ONE_SIDED_DEACTIVATED'::character varying, 'TWO_SIDED_DEACTIVATED'::character varying, 'MANUAL_DEACTIVATED'::character varying])::text[])),
    tariff_id        integer
        constraint fkonogsa1wh4rfjc81nxr1bvgqs
            references tariffs,
    user_id          integer
        constraint fkbg4rwg1euxxahlowp4b4xgjth
            references users
);

alter table numbers
    owner to postgres;
