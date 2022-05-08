create table wow.corp_employee
(
    corp_id     bigint      not null,
    employee_id varchar(30) not null,
    primary key (corp_id, employee_id)
);

create table wow.corporation
(
    corp_id       bigint auto_increment
        primary key,
    company_name  varchar(30) null,
    register_code varchar(30) null,
    constraint corporation_corp_id_uindex
        unique (corp_id)
)
    auto_increment = 119;

create index corp_name_index
    on wow.corporation (company_name);

create table wow.coupons
(
    coupon_id  bigint auto_increment
        primary key,
    valid_from timestamp null,
    valid_to   timestamp null,
    batch_id   bigint    null,
    user_id    bigint    null,
    is_used    tinyint   null,
    constraint coupons_coupon_id_uindex
        unique (coupon_id)
)
    auto_increment = 68;

create index coupons_batch_id_user_id_index
    on wow.coupons (batch_id, user_id);

create table wow.coupons_batch
(
    batch_id    bigint auto_increment
        primary key,
    stock       int           null,
    discount    decimal(4, 2) not null,
    coupon_type char          null,
    details     varchar(60)   null,
    constraint coupons_batch_batch_id_uindex
        unique (batch_id)
)
    auto_increment = 147;

create table wow.individual
(
    user_id           bigint      not null
        primary key,
    driver_licence    varchar(60) not null,
    insurance_company varchar(60) not null,
    insurance_number  varchar(60) not null,
    constraint individual_user_id_uindex
        unique (user_id)
);

create table wow.invoice
(
    invoice_id   bigint auto_increment
        primary key,
    invoice_date timestamp      null,
    amount       decimal(10, 2) null,
    constraint invoice_invoice_id_uindex
        unique (invoice_id)
)
    auto_increment = 47;

create table wow.payment
(
    payment_id bigint auto_increment
        primary key,
    card_id    int         not null,
    pay_amount varchar(30) not null,
    Invoice_id bigint      not null,
    Pay_date   timestamp   not null,
    constraint payment_payment_id_uindex
        unique (payment_id)
)
    auto_increment = 39;

create table wow.payment_card
(
    card_id     int auto_increment
        primary key,
    user_id     int         not null,
    card_num    varchar(30) not null,
    expire_date varchar(10) not null,
    fname       varchar(60) not null,
    lname       varchar(60) not null,
    country     varchar(60) null,
    state       varchar(60) null,
    city        varchar(60) null,
    street      varchar(60) null,
    zipcode     varchar(60) null,
    status      char        null,
    constraint payment_card_card_id_uindex
        unique (card_id)
)
    auto_increment = 30;

create table wow.rental_order
(
    order_id             bigint auto_increment,
    vin_id               varchar(30)    null,
    user_id              bigint         null,
    invoice_id           bigint         null,
    coupon_id            bigint         null,
    pick_loc_id          int            null,
    drop_loc_id          int            null,
    pick_date            timestamp      null,
    drop_date            timestamp      null,
    expected_date        timestamp      null,
    start_odometer       decimal(7, 2)  null,
    end_odometer         decimal(7, 2)  null,
    daily_limit_odometer decimal(7, 2)  null,
    order_status         int            null,
    basic_cost           decimal(10, 2) null,
    extra_cost           decimal(10, 2) null,
    constraint rental_order_order_id_uindex
        unique (order_id)
)
    auto_increment = 67;

create table wow.user
(
    id          bigint auto_increment
        primary key,
    email       varchar(50) null,
    password    varchar(60) null,
    role_type   char        null,
    lname       varchar(30) null,
    fname       varchar(30) null,
    employee_id varchar(30) null,
    company_id  bigint      null,
    phone_num   varchar(30) null,
    constraint user_email_password_uindex
        unique (email, password),
    constraint user_id_uindex
        unique (id)
)
    auto_increment = 958;

create table wow.user_address
(
    user_id bigint      not null
        primary key,
    country varchar(60) null,
    state   varchar(60) null,
    city    varchar(60) null,
    street  varchar(60) null,
    zipcode varchar(60) null,
    constraint user_address_address_id_uindex
        unique (user_id)
);

create table wow.yzm_car_class
(
    class_id            int auto_increment
        primary key,
    class_type          varchar(30)    not null,
    image_url           varchar(700)   null,
    rental_rate_per_day varchar(30)    not null,
    daily_mile_limit    decimal(10, 2) not null,
    over_fee            decimal(10, 2) not null,
    constraint yzm_car_class_class_id_uindex
        unique (class_id)
)
    auto_increment = 15;

create table wow.yzm_manufacture
(
    man_id   int auto_increment
        primary key,
    man_name varchar(30) not null,
    constraint yzm_manufacture_man_id_uindex
        unique (man_id)
)
    auto_increment = 15;

create table wow.yzm_model
(
    model_id   int auto_increment
        primary key,
    man_id     int         not null,
    model_name varchar(30) not null,
    year       varchar(20) not null,
    seat_num   int         not null,
    constraint yzm_model_model_id_uindex
        unique (model_id),
    constraint man_id_fk
        foreign key (man_id) references wow.yzm_manufacture (man_id)
)
    auto_increment = 15;

create table wow.yzm_office
(
    office_id int auto_increment
        primary key,
    name      varchar(30) not null,
    country   varchar(30) not null,
    state     varchar(30) not null,
    city      varchar(30) not null,
    street    varchar(30) not null,
    zipcode   varchar(30) not null,
    phone_num varchar(20) not null,
    constraint yzm_office_office_id_uindex
        unique (office_id)
)
    auto_increment = 22;

create table wow.yzm_vehicles
(
    vin_id       varchar(30) not null
        primary key,
    plate_number varchar(20) not null,
    class_id     int         not null,
    status       char        not null,
    office_id    int         not null,
    model_id     int         not null,
    constraint yzm_vehiclesl_vinl_id_uindex
        unique (vin_id),
    constraint class_id_fk
        foreign key (class_id) references wow.yzm_car_class (class_id),
    constraint model_id_fk
        foreign key (model_id) references wow.yzm_model (model_id),
    constraint office_id_fk
        foreign key (office_id) references wow.yzm_office (office_id)
);

