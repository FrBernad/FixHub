TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE jobs RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE reviews RESTART IDENTITY AND COMMIT NO CHECK;


-- PROVIDER
insert into USERS(u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES ('password', 'Ignacio', 'Lopez', 'ignacio@yopmail.com', '5491112345678', null, null, 'Caballito', 'CABA');


-- NOT_VERIFIED
insert into USERS(u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES ('password', 'Raul', 'Perez', 'raul@yopmail.com', '5491187654321', null, null, 'Recoleta', 'CABA');


-- CLIENT
insert into USERS(u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES ('password', 'Pedro', 'Romero', 'pedro@yopmail.com', '5491109876543', null, null, 'Once', 'CABA');

insert into ROLES(r_role, r_user_id)
VALUES ('USER', 1);
insert into ROLES(r_role, r_user_id)
VALUES ('VERIFIED', 1);
insert into ROLES(r_role, r_user_id)
VALUES ('PROVIDER', 1);

insert into ROLES(r_role, r_user_id)
VALUES ('USER', 2);
insert into ROLES(r_role, r_user_id)
VALUES ('NOT_VERIFIED', 2);

insert into ROLES(r_role, r_user_id)
VALUES ('USER', 3);
insert into ROLES(r_role, r_user_id)
VALUES ('VERIFIED', 3);


INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price)
VALUES ('Limpieza total', 'MECANICO', 'Limpieza de filtros', 1, 300);
INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price)
VALUES ('Soluciono problemas de tensión', 'ELECTRICISTA', 'Revisión de tableros', 1, 300);
INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price)
VALUES ('Instalación de riegos de ultima generación', 'PLOMERO', 'Instalación de riegos', 1, 300);
INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price)
VALUES ('Corto el pasto y hago plantaciones', 'JARDINERO', 'Corte de pasto', 1, 300);
INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price)
VALUES ('Descripción de trabajo', 'GASISTA', 'Instalación de medidor de gas', 1, 300);
INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price)
VALUES ('Descripcion de trbajo', 'CARPINTERO', 'Armado de muebles', 1, 300);

insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date)
VALUES ('Muy buen trabajo, gracias por todo', 1, 1, '2021-04-05 20:26:02');
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date)
VALUES ('Muy buen trabajo, gracias por todo', 1, 1, '2021-04-05 20:26:02');
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date)
VALUES ('Muy buen trabajo, gracias por todo', 2, 5, '2021-04-05 20:26:02');
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date)
VALUES ('Muy buen trabajo, gracias por todo', 1, 5, '2021-04-05 20:26:02');

INSERT INTO CONTACT_INFO(ci_user_id, ci_city, ci_state, ci_street, ci_address_number, ci_floor, ci_department_number)
VALUES (3,'Lomas de Zamora','Buenos Aires', 'Spiro','312','21','B');
INSERT INTO CONTACT_INFO(ci_user_id, ci_city, ci_state, ci_street, ci_address_number, ci_floor, ci_department_number)
VALUES (3,'Retiro','Buenos Aires', 'Independencia','15','29','A');

INSERT INTO CONTACT(c_provider_id, c_user_id, c_job_id, c_info_id, c_message, c_date)
VALUES (1,3,1,1,'Te necesito rápido!','2021-04-05 20:26:02');

-- INSERT INTO CONTACT(c_provider_id, c_user_id, c_job_id, c_info_id, c_message, c_date)
-- VALUES (1,3,1,1,'Te necesito rápido!','2021-04-05 20:26:02');


