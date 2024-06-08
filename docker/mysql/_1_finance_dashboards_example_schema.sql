create table category
(
    id              int auto_increment
        primary key,
    name            varchar(128) not null,
    parent_id int          null
);

create table institution
(
    id   int auto_increment
        primary key,
    name varchar(128) not null
)
    comment 'Banks, Credit Cards, etc';

create table account
(
    id           int auto_increment
        primary key,
    name         varchar(256) not null,
    institution_id  int          null,
    balance      double       not null,
    alias        varchar(256) null,
    type         varchar(256) null,
    last_updated datetime     null,
    constraint account_institution_id_fk
        foreign key (institution_id) references institution (id)
);

create table vendor
(
    id      int auto_increment
        primary key,
    name    varchar(256) not null,
    aliases varchar(512) null
);

create table transaction
(
    id                   int auto_increment
        primary key,
    date                 datetime     not null,
    description          varchar(256) null,
    original_description varchar(256) null,
    amount               double       null,
    transaction_type     varchar(16)  null,
    category_id             int          not null,
    vendor_id             int          not null,
    account_id              int          not null,
    mail_message_id      varchar(128) not null,
    notes                varchar(256) null,
    constraint transaction_account_id_fk
        foreign key (account_id) references account (id),
    constraint transaction_category_id_fk
        foreign key (category_id) references category (id),
    constraint transaction_vendor_id_fk
        foreign key (vendor_id) references vendor (id)
);

SET PERSIST sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
