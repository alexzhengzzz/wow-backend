create table `wow-payment`.bill
(
    bill_id     bigint auto_increment,
    bill_amount decimal(14, 2) default 0.00 not null,
    card_num    bigint                      not null,
    status      int            default 0    not null,
    constraint bill_bill_uindex
        unique (bill_id)
)
    auto_increment = 90;

create table `wow-payment`.card_info
(
    card_num     bigint                      not null
        primary key,
    fname        varchar(30)                 not null,
    lname        varchar(30)                 not null,
    country      varchar(30)                 null,
    state        varchar(30)                 null,
    city         varchar(30)                 null,
    zipcode      varchar(30)                 null,
    cvv          int                         not null,
    expired_date varchar(30)                 not null,
    balance      decimal(14, 2) default 0.00 null,
    constraint card_info_card_num_uindex
        unique (card_num)
);

create index card_info_card_num_cvv_expired_date_index
    on `wow-payment`.card_info (card_num, cvv, expired_date);

