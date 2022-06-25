drop table if exists member;
drop table if exists member_authority;
drop table if exists member_social;
drop table if exists member_suspension;


create or replace table member
(
    idx         bigint unsigned auto_increment
        primary key,
    member_id   varchar(255) not null,
    password    varchar(255) not null,
    name        varchar(255) not null,
    email       varchar(255) null,
    created_at  datetime     null,
    modified_at datetime     null,
    deleted_at  datetime     null,
    constraint member_id_uindex
        unique (member_id)
);

create or replace table member_authority
(
    idx         bigint unsigned auto_increment
        primary key,
    member_idx  bigint unsigned not null,
    auth_level  varchar(255)    not null,
    created_at  datetime        null,
    modified_at datetime        null,
    deleted_at  datetime        null,
    constraint member_authority_member_idx_uindex
        unique (member_idx)
);

create or replace table member_social
(
    idx         bigint unsigned auto_increment
        primary key,
    member_idx  bigint unsigned not null,
    social_type varchar(255)    null,
    social_id   varchar(255)    null,
    created_at  datetime        null,
    modified_at datetime        null,
    deleted_at  datetime        null,
    constraint member_social_member_idx_social_type_uindex
        unique (member_idx, social_type)
);

create or replace table member_suspension
(
    idx          bigint unsigned auto_increment
        primary key,
    member_idx   bigint unsigned not null,
    reason       text            null,
    suspended_at datetime        null,
    suspended_to datetime        null,
    created_at   datetime        null,
    modified_at  datetime        null,
    deleted_at   datetime        null
);

