create table tariffs
(
    charge_per_minute  double precision,
    charge_permb       double precision,
    charge_persms      double precision,
    id                 serial
        primary key,
    internet_amount    double precision,
    minute_amount      integer,
    monthly_price      double precision,
    sms_amount         integer,
    subscription_price double precision,
    valid_period       integer,
    description        varchar(255),
    name               varchar(255)
);

