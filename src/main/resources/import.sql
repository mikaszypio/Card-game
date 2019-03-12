insert into app_role (name) values ('administrator'), ('player'), ('temporary');
insert into app_user (username, password, role_id) values ('administrator', 'poweruser', 1);
insert into app_user (username, password, role_id) values ('user01', 'pass01', 2), ('user02', 'pass02', 2), ('user03', 'pass03', 2), ('user04', 'pass04', 2), ('user05', 'pass05', 2), ('user06', 'pass06', 2), ('user07', 'pass07', 2), ('user08', 'pass08', 2), ('user09', 'pass09', 2);
