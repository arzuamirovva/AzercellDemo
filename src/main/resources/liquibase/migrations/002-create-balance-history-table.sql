create table balance_history
(
    amount           double precision,
    id               serial
        primary key,
    number_id        integer
        constraint fkandrh967hdlwdast9y6b0f3h9
            references numbers,
    user_id          integer,
    transaction_date timestamp(6)
);

alter table balance_history
    owner to postgres;



