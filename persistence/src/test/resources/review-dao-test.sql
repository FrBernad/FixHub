TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE jobs RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE reviews RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;


insert into USERS(u_id, u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES (1, 'password','Juan','Gomez','juan@yopmail.com','5491187654321',null,null,'Palermo','CABA');

insert into USERS(u_id, u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES (2, 'password','John','Doe','john@yopmail.com','5491112345678',null,null,'Buenos Aires','Banfield');

insert into ROLES(r_id,r_role, r_user_id)
VALUES (1,'USER', 1);
insert into ROLES(r_id,r_role, r_user_id)
VALUES (2,'VERIFIED', 1);
insert into ROLES(r_id,r_role, r_user_id)
VALUES (3,'PROVIDER', 1);

insert into ROLES(r_id,r_role, r_user_id)
VALUES (6,'USER', 2);
insert into ROLES(r_id,r_role, r_user_id)
VALUES (7,'VERIFIED', 2);


insert into JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('El mejor cambio de aceite', 'MECANICO', 'Cambio de aceite', 1,3500.50,false);

INSERT INTO CONTACT_INFO(ci_id,ci_user_id, ci_city, ci_state, ci_street, ci_address_number, ci_floor, ci_department_number)
VALUES (1,2,'Lomas de Zamora','Buenos Aires', 'Spiro','312','21','B');
INSERT INTO CONTACT_INFO(ci_id,ci_user_id, ci_city, ci_state, ci_street, ci_address_number, ci_floor, ci_department_number)
VALUES (2,2,'Retiro','Buenos Aires', 'Independencia','15','29','A');

INSERT INTO CONTACT(c_id,c_provider_id, c_user_id, c_job_id, c_info_id, c_message, c_date)
VALUES (1,1,2, 1,1,'Te necesito rápido!','2021-04-05 20:26:02');

insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES('Muy buen trabajo, gracias por todo', 1, 4, '2021-04-05 20:26:03',2);
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES('Muy buen trabajo, gracias por todo', 1, 4, '2021-04-05 20:26:20',2);
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES('Muy buen trabajo, gracias por todo', 1, 4, '2021-04-05 20:26:50',2);
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES('Muy buen trabajo, gracias por todo', 1, 4, '2021-04-05 20:26:55',2);
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES('Muy buen trabajo, gracias por todo', 1, 4, '2021-04-05 20:26:57',2);

