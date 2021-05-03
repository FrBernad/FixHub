TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE jobs RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE reviews RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE contact RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE contact_info RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE user_location RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE states RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE cities RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE user_schedule RESTART IDENTITY AND COMMIT NO CHECK;






-- PROVIDER
insert into USERS(u_id,u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES (1,'password', 'Ignacio', 'Lopez', 'ignacio@yopmail.com', '5491112345678', null, null, 'Caballito', 'CABA');


-- NOT_VERIFIED
insert into USERS(u_id,u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES (2,'password', 'Raul', 'Perez', 'raul@yopmail.com', '5491187654321', null, null, 'Recoleta', 'CABA');


-- CLIENT
insert into USERS(u_id,u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES (3,'password', 'Pedro', 'Romero', 'pedro@yopmail.com', '5491109876543', null, null, 'Once', 'CABA');

insert into ROLES(r_id,r_role, r_user_id)
VALUES (1,'USER', 1);
insert into ROLES(r_id,r_role, r_user_id)
VALUES (2,'VERIFIED', 1);
insert into ROLES(r_id,r_role, r_user_id)
VALUES (3,'PROVIDER', 1);

insert into ROLES(r_id,r_role, r_user_id)
VALUES (4,'USER', 2);
insert into ROLES(r_id,r_role, r_user_id)
VALUES (5,'NOT_VERIFIED', 2);

insert into ROLES(r_id,r_role, r_user_id)
VALUES (6,'USER', 3);
insert into ROLES(r_id,r_role, r_user_id)
VALUES (7,'VERIFIED', 3);


INSERT INTO JOBS(j_id,j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES (1,'Limpieza total', 'MECANICO', 'Limpieza de filtros', 1, 300,false);
INSERT INTO JOBS(j_id,j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES (2,'Soluciono problemas de tensión', 'ELECTRICISTA', 'Revisión de tableros', 1, 300,false);
INSERT INTO JOBS(j_id,j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES (3,'Instalación de riegos de ultima generación', 'PLOMERO', 'Instalación de riegos', 1, 300,false);
INSERT INTO JOBS(j_id,j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES (4,'Corto el pasto y hago plantaciones', 'JARDINERO', 'Corte de pasto', 1, 300,false);
INSERT INTO JOBS(j_id,j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES (5,'Descripción de trabajo', 'GASISTA', 'Instalación de medidor de gas', 1, 300,false);
INSERT INTO JOBS(j_id,j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES (6,'Descripcion de trbajo', 'CARPINTERO', 'Armado de muebles', 1, 300,false);

insert into REVIEWS(r_id,r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES (1,'Muy buen trabajo, gracias por todo', 1, 1, '2021-04-05 20:26:02',3);
insert into REVIEWS(r_id,r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES (2,'Muy buen trabajo, gracias por todo', 1, 1, '2021-04-05 20:26:02',3);
insert into REVIEWS(r_id,r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES (3,'Muy buen trabajo, gracias por todo', 2, 5, '2021-04-05 20:26:02',3);
insert into REVIEWS(r_id,r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES (4,'Muy buen trabajo, gracias por todo', 1, 5, '2021-04-05 20:26:02',3);

INSERT INTO CONTACT_INFO(ci_id,ci_user_id, ci_city, ci_state, ci_street, ci_address_number, ci_floor, ci_department_number)
VALUES (1,3,'Lomas de Zamora','Buenos Aires', 'Spiro','312','21','B');
INSERT INTO CONTACT_INFO(ci_id,ci_user_id, ci_city, ci_state, ci_street, ci_address_number, ci_floor, ci_department_number)
VALUES (2,3,'Retiro','Buenos Aires', 'Independencia','15','29','A');

INSERT INTO CONTACT(c_id,c_provider_id, c_user_id, c_job_id, c_info_id, c_message, c_date)
VALUES (1,1,3,1,1,'Te necesito rápido!','2021-04-05 20:26:02');

-- INSERT INTO CONTACT(c_provider_id, c_user_id, c_job_id, c_info_id, c_message, c_date)
-- VALUES (1,3,1,1,'Te necesito rápido!','2021-04-05 20:26:02');

INSERT INTO STATES(s_id, s_name) VALUES(1, 'Buenos Aires');

INSERT INTO CITIES(c_id, c_state_id, c_name) VALUES(1, 1, '25 de Mayo');
INSERT INTO CITIES(c_id, c_state_id, c_name) VALUES(2, 1, '3 de febrero');
INSERT INTO CITIES(c_id, c_state_id, c_name) VALUES(3, 1, 'A. Alsina');

INSERT INTO USER_LOCATION(ul_city_id, ul_user_id) VALUES (1, 1);
INSERT INTO USER_LOCATION(ul_city_id, ul_user_id) VALUES (2, 1);
INSERT INTO USER_LOCATION(ul_city_id, ul_user_id) VALUES (3, 1);

INSERT INTO USER_SCHEDULE(us_user_id, us_start_time, us_end_time) VALUES(1, '11:00', '18:00');


