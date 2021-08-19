create table if not exists quotes
(
    id          bigserial primary key,
    isin        varchar not null,
    bid         decimal,
    ask         decimal,
    date        timestamptz
);

create table if not exists elvls
(
    id              bigserial primary key,
    isin            varchar unique not null,
    elvl_value      decimal not null
);
