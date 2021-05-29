TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE jobs RESTART IDENTITY AND COMMIT NO CHECK;

insert into USERS
VALUES (1, 'city', 'email', 'name', 'password', 'phoneNumber', 'state', 'surname', null, null, null, null);
INSERT INTO roles(r_user_id, r_role) VALUES (1,'USER');
INSERT INTO roles(r_user_id, r_role) VALUES (1,'PROVIDER');

insert into USERS(u_id, u_city, u_email, u_name, u_password, u_phone_number, u_state, u_surname, u_cover_picture, u_profile_picture, u_location_id, u_schedule_id)
VALUES (2, 'city2', 'email2', 'name2', 'password2', 'phoneNumber2', 'state2', 'surname2', null, null, null, null);
INSERT INTO roles(r_user_id, r_role) VALUES (2,'USER');
INSERT INTO roles(r_user_id, r_role) VALUES (2,'PROVIDER');

INSERT INTO states(s_id, s_name) VALUES (2,'Bs as');
INSERT INTO cities(c_id, c_name, c_state_id) VALUES (1,'La pampa',2);

INSERT INTO provider_location(pl_id, pl_provider_id, pl_state_id) VALUES (1,1,2);
INSERT INTO provider_location(pl_id, pl_provider_id, pl_state_id) VALUES (2,2,2);

INSERT INTO provider_cities(pc_provider_id, pc_city_id) VALUES (1,1);
INSERT INTO provider_cities(pc_provider_id, pc_city_id) VALUES (2,1);

UPDATE USERS SET u_location_id = 1 WHERE u_id=1;
UPDATE USERS SET u_location_id = 2 WHERE u_id=2;

INSERT INTO JOBS(j_id, j_description, j_category, j_job_provided, j_provider_id, j_price, j_paused)
VALUES (2, 'Limpieza total', 'MECANICO', 'Limpieza de filtros', 1, 300, false);

INSERT INTO JOBS(j_id, j_description, j_category, j_job_provided, j_provider_id, j_price, j_paused)
VALUES (1, 'Limpieza total', 'MECANICO', 'Limpieza de filtros', 1, 800, false);

INSERT INTO JOBS(j_id, j_description, j_category, j_job_provided, j_provider_id, j_price, j_paused)
VALUES (3, 'Limpieza total', 'MECANICO', 'Limpieza de filtros', 1, 500, false);

INSERT INTO JOBS(j_id, j_description, j_category, j_job_provided, j_provider_id, j_price, j_paused)
VALUES (4, 'Soluciono errores de tensión', 'ELECTRICISTA', 'Revisión de tableros', 1, 900, false);

INSERT INTO JOBS(j_id, j_description, j_category, j_job_provided, j_provider_id, j_price, j_paused)
VALUES (5, 'Soluciono problemas de tensión', 'ELECTRICISTA', 'Revisión de tableros', 1, 900, false);

INSERT INTO JOBS(j_id, j_description, j_category, j_job_provided, j_provider_id, j_price, j_paused)
VALUES (6, 'Soluciono cambios de tensión', 'ELECTRICISTA', 'Revisión de tableros', 1, 1500, false);

INSERT INTO JOBS(j_id, j_description, j_category, j_job_provided, j_provider_id, j_price, j_paused)
VALUES (7, 'Limpio techos', 'TECHISTA', 'Colocación de vidrio en ventanales', 1, 1800, false);

INSERT INTO JOBS(j_id, j_description, j_category, j_job_provided, j_provider_id, j_price, j_paused)
VALUES (8, 'Limpio techos', 'TECHISTA', 'Colocación de vidrio en ventanales', 1, 1500, false);

INSERT INTO JOBS(j_id, j_description, j_category, j_job_provided, j_provider_id, j_price, j_paused)
VALUES (9, 'Limpio techos', 'TECHISTA', 'Colocación de vidrio en ventanales', 1, 2000, false);

INSERT INTO JOBS(j_id, j_description, j_category, j_job_provided, j_provider_id, j_price, j_paused)
VALUES (10, 'Limpio techos', 'TECHISTA', 'Colocación de vidrio en ventanales', 2, 3000, false);

insert into REVIEWS(r_id,r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES(1,'review', 1, 5, '2021-04-05',1);
insert into REVIEWS(r_id,r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES(2,'review', 2, 3, '2021-04-05',1);
insert into REVIEWS(r_id,r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES(3,'review', 3, 2, '2021-04-05',1);
insert into REVIEWS(r_id,r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES(4,'review', 4, 1, '2021-04-05',1);