insert into member(id, password, name, email, created_at, modified_at, deleted_at) values('choimory', 'asdqwe123', '중윤최', 'choimory@naver.com', '2000-01-01 00:00:00', '2000-01-01 00:00:00', null);
insert into member_authority(id, member_id, auth_level, created_at, modified_at, deleted_at) values (1, 'choimory', 'MEMBER', '2000-01-01 00:00:00', '2000-01-01 00:00:00', null);