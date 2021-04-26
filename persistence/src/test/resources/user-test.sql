TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE jobs RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE reviews RESTART IDENTITY AND COMMIT NO CHECK;


insert into USERS(u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES ('password','Ignacio','Lopez','ignacio@yopmail.com','5491112345678',null,null,'Caballito','CABA');
insert into ROLES(r_role, r_user_id) VALUES ('USER',1);
insert into ROLES(r_role, r_user_id) VALUES ('VERIFIED',1);
insert into ROLES(r_role, r_user_id) VALUES ('PROVIDER',1);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price)
VALUES ('Limpieza total','MECANICO','Limpieza de filtros',1,300);
INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price)
VALUES ('Soluciono problemas de tensión','ELECTRICISTA','Revisión de tableros',1,300);
INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price)
VALUES ('Instalación de riegos de ultima generación','PLOMERO','Instalación de riegos',1,300);
INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price)
VALUES ('Corto el pasto y hago plantaciones','JARDINERO','Corte de pasto',1,300);
INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price)
VALUES ('Descripción de trabajo','GASISTA','Instalación de medidor de gas',1,300);
INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price)
VALUES ('Descripcion de trbajo','CARPINTERO','Armado de muebles',1,300);

insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date)
VALUES('Muy buen trabajo, gracias por todo', 1, 1, '2021-04-05 20:26:02');
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date)
VALUES('Muy buen trabajo, gracias por todo', 1, 1, '2021-04-05 20:26:02');
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date)
VALUES('Muy buen trabajo, gracias por todo', 2, 5, '2021-04-05 20:26:02');
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date)
VALUES('Muy buen trabajo, gracias por todo', 1, 5, '2021-04-05 20:26:02');


insert into USERS(u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES ('password','Raul','Perez','raul@yopmail.com','5491187654321',null,null,'Recoleta','CABA');
insert into ROLES(r_role, r_user_id) VALUES ('USER',1);
insert into ROLES(r_role, r_user_id) VALUES ('NOT_VERIFIED',1);
