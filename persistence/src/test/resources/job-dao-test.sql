TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE jobs RESTART IDENTITY AND COMMIT NO CHECK;

insert into USERS
VALUES (1,'password', 'name', 'surname', 'email', 'phoneNumber', null, null, 'state', 'city');
INSERT INTO roles VALUES (1,'USER',1);
INSERT INTO roles VALUES (2,'PROVIDER',1);

insert into USERS
VALUES (2,'password', 'name', 'surname', 'email2', 'phoneNumber', null, null, 'state', 'city');
INSERT INTO roles VALUES (3,'USER',2);
INSERT INTO roles VALUES (4,'PROVIDER',2);

INSERT INTO states VALUES (2,'Bs as');
INSERT INTO cities VALUES (1,2,'La pampa');

INSERT INTO user_location VALUES (1,1);
INSERT INTO user_location VALUES (1,2);


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

insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES('review', 1, 5, '2021-04-05 20:26:02',1);
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES('review', 2, 3, '2021-04-05 20:26:02',1);
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES('review', 3, 2, '2021-04-05 20:26:02',1);
insert into REVIEWS(r_description, r_job_id, r_rating, r_creation_date,r_reviewer_id)
VALUES('review', 4, 1, '2021-04-05 20:26:02',1);