drop table if exists member;
drop table if exists member_authority;
drop table if exists member_social;
drop table if exists member_suspension;


create or replace table member
(
    id  varchar(255) not null
        primary key,
    password    varchar(255) not null,
    name        varchar(255) not null,
    email       varchar(255) null,
    created_at  datetime     null,
    modified_at datetime     null,
    deleted_at  datetime     null
);

create or replace table member_authority
(
    id         bigint unsigned auto_increment
        primary key,
    member_id  varchar(255) not null,
    auth_level  varchar(255)    not null,
    created_at  datetime        null,
    modified_at datetime        null,
    deleted_at  datetime        null,
    constraint member_authority_member_id_uindex
        unique (member_id)
);

create or replace table member_social
(
    id         bigint unsigned auto_increment
        primary key,
    member_id  varchar(255) not null,
    social_type varchar(255)    null,
    social_id   varchar(255)    null,
    created_at  datetime        null,
    modified_at datetime        null,
    deleted_at  datetime        null,
    constraint member_social_member_id_social_type_uindex
        unique (member_id, social_type)
);

create or replace table member_suspension
(
    id          bigint unsigned auto_increment
        primary key,
    member_id   varchar(255) not null,
    reason       text            null,
    suspended_at datetime        null,
    suspended_to datetime        null,
    created_at   datetime        null,
    modified_at  datetime        null,
    deleted_at   datetime        null
);

