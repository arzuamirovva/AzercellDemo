create table cards
(
    balance  double precision,
    exp_date date,
    id       serial
        primary key,
    user_id  integer
        constraint fkcmanafgwbibfijy2o5isfk3d5
            references users,
    cvv      varchar(255),
    number   varchar(255)
);

alter table cards
    owner to postgres;
