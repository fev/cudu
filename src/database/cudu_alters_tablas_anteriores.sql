ALTER TABLE ASOCIADO ADD COLUMN usuario varchar(200);
ALTER TABLE ASOCIADO  ADD CONSTRAINT u_asociado_usuario UNIQUE(usuario);
ALTER TABLE ASOCIADO ADD CONSTRAINT fk_asociado_users FOREIGN KEY (usuario) REFERENCES users(username);


insert into users
(username, password, fullname, idgrupo, enabled)
values
('albafo','albafo2011','cuenta interesante','UP','T');



insert into authorities
(username, authority)
values
('albafo','PERMISO_H');

update asociado set usuario = 'albafo' where id=20;



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








insert into authorities (username,authority) 
values 
('PACO', 'ROLE_USER');

insert into authorities (username,authority) 
values 
('PACO', 'ROLE_ADMIN');

insert into authorities (username,authority) 
values 
('PACO', 'ROLE_PERMISO_A');

insert into authorities (username,authority) 
values 
('HECTOR', 'ROLE_USER');

insert into authorities (username,authority) 
values 
('HECTOR', 'ROLE_ADMIN');

insert into authorities (username,authority) 
values 
('HECTOR', 'ROLE_PERMISO_B');

insert into authorities (username,authority) 
values 
('JESTRICKGA', 'ROLE_USER');

insert into authorities (username,authority) 
values 
('JESTRICKGA', 'ROLE_PERMISO_F');


insert into authorities (username,authority) 
values 
('TECSDC', 'ROLE_USER');

insert into authorities (username,authority) 
values 
('TECSDC', 'ROLE_PERMISO_C1');