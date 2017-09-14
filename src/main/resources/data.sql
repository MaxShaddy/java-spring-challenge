--create table role_per_user (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id))
--create table user (id bigint not null, email varchar(255), first_name varchar(255), last_name varchar(255), primary key (id))
--create table user_role (id bigint not null, role_name varchar(255), primary key (id))
-- ROLES
 
insert into user_role VALUES (1 , 'ADMIN');
insert into user_role VALUES (2 , 'MANAGER');
insert into user_role VALUES (3 , 'USER');
insert into user_role VALUES (4 , 'CEO');

-- USERS

insert into user VALUES (1, 'Bob.Iger@hwr.com',		'Bob', 'Iger' );
insert into role_per_user VALUES (1, 2);
insert into role_per_user VALUES (1, 3);

insert into user VALUES (2, 'Bob.Kennedy@hwr.com',		'Bob', 'Kennedy' );
insert into role_per_user VALUES (2, 2);

insert into user VALUES (3, 'Steve.Burke@hwr.com',	'Steve', 'Burke' );
insert into role_per_user VALUES (3, 3);

insert into user VALUES (4, 'Leslie.Moonves@hwr.com',	'Leslie', 'Moonves' );
insert into role_per_user VALUES (4, 3);

insert into user VALUES (6, 'Shari.Redstone@hwr.com',	'Shari', 'Redstone' );
insert into role_per_user VALUES (6, 3);

insert into user VALUES (7, 'Jeff.Bewkes@hwr.com',	'Jeff', 'Bewkes' );
insert into role_per_user VALUES (7, 3);

insert into user VALUES (8, 'Alan.Horn@hwr.com',	'Alan', 'Horn' );
insert into role_per_user VALUES (8, 1);
insert into role_per_user VALUES (8, 2);

insert into user VALUES (9, 'Oprah.Winfrey@hwr.com',	'Oprah', 'Winfrey' );
insert into role_per_user VALUES (9, 3);
insert into role_per_user VALUES (9, 4);

insert into user VALUES (10, 'Peter.Rice@hwr.com',	'Peter', 'Rice' );
insert into role_per_user VALUES (10, 1);

insert into user VALUES (16, 'Dana.Walden@hwr.com',	'Dana', 'Walden' );
insert into role_per_user VALUES (16, 4);


insert into user VALUES (17, 'John.Lasseter@hwr.com',	'John', 'Lasseter' );
insert into role_per_user VALUES (17, 2);
insert into role_per_user VALUES (17, 3);

insert into user VALUES (18, 'Casey.Bloys@hwr.com',	'Casey', 'Bloys' );
insert into role_per_user VALUES (18, 1);
insert into role_per_user VALUES (18, 4);




--select user0_.id as id1_1_, user0_.email as email2_1_, user0_.first_name as first_na3_1_, user0_.last_name as last_nam4_1_ 
--from user user0_ where upper(user0_.first_name) like upper(?) or upper(user0_.last_name) like upper(?) or upper(user0_.email) like upper(?)