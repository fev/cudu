select * from users;
insert into users(username, password, fullname, idgrupo, enabled)
values ('HECTOR','test','Hector, usu con permiso b','UP',true);

insert into users(username, password, fullname, idgrupo, enabled)
values ('PACO','test','paco,usu con permiso a','UP',true);

insert into users(username, password, fullname, idgrupo, enabled)
values ('TECSDC','test','tecnico del sdc','UP',true);

insert into users(username, password, fullname, idgrupo, enabled)
values ('JESTRICKGA','test','kraal o comite con permiso F','UP',true);


update asociado set usuario = 'JESTRICKGA' where id = 3;