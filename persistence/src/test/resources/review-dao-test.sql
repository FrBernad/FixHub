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
VALUES (6,'USER', 2);
insert into ROLES(r_id,r_role, r_user_id)
VALUES (7,'VERIFIED', 2);


insert into JOBS(j_id,j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES (1,'El mejor cambio de aceite', 'MECANICO', 'Cambio de aceite', 1,3500,false);

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

