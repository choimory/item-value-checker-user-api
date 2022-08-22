create or replace table item_value_checker_user.member
(
    id          bigint unsigned auto_increment
        primary key,
    identity     varchar(255) not null,
    password    varchar(255) not null,
    nickname    varchar(255) not null,
    email       varchar(255) null,
    created_at  datetime     null,
    modified_at datetime     null,
    deleted_at  datetime     null
);

create or replace table item_value_checker_user.member_authority
(
    id          bigint unsigned auto_increment
        primary key,
    member_id   bigint unsigned not null,
    auth_level  varchar(255)    not null,
    created_at  datetime        null,
    modified_at datetime        null,
    deleted_at  datetime        null
);

create or replace table item_value_checker_user.member_social
(
    id          bigint unsigned auto_increment
        primary key,
    member_id   bigint unsigned not null,
    social_type varchar(255)    null,
    social_id   varchar(255)    null,
    created_at  datetime        null,
    modified_at datetime        null,
    deleted_at  datetime        null
);

create or replace table item_value_checker_user.member_suspension
(
    id           bigint unsigned auto_increment
        primary key,
    member_id    bigint unsigned not null,
    reason       text            null,
    suspended_at datetime        null,
    suspended_to datetime        null,
    created_at   datetime        null,
    modified_at  datetime        null,
    deleted_at   datetime        null
);

