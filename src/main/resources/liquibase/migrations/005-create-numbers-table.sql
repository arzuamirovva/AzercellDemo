create table numbers
(
    balance          double precision,
    free_internet    integer,
    free_minutes     integer,
    has_chance       boolean,
    id               serial
        primary key,
    internet_balance double precision,
    minute_balance   integer,
    sms_balance      integer,
    tariff_id        integer
        constraint fkonogsa1wh4rfjc81nxr1bvgqs
            references tariffs,
    user_id          integer
        constraint fkbg4rwg1euxxahlowp4b4xgjth
            references users,
    assign_time      timestamp(6),
    last_spin_time   timestamp(6),
    number           varchar(255),
    password         varchar(255),
    status           varchar(255)
        constraint numbers_status_check
            check ((status)::text = ANY
        ((ARRAY ['ACTIVE'::character varying, 'ONE_SIDED_DEACTIVATED'::character varying, 'TWO_SIDED_DEACTIVATED'::character varying, 'MANUAL_DEACTIVATED'::character varying])::text[]))
    );

alter table numbers
    owner to postgres;

