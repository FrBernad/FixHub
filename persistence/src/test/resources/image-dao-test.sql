TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE jobs RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE job_image RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;


INSERT INTO users(u_id,u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES (1,'12345678','Pedro','De La Fuentes','Pedro123@yopmail.com','1587654321',null,null,'Rosario','Santa Fe');


insert into ROLES(r_id,r_role, r_user_id)
VALUES (1,'USER', 1);
insert into ROLES(r_id,r_role, r_user_id)
VALUES (2,'VERIFIED', 1);
insert into ROLES(r_id,r_role, r_user_id)
VALUES (3,'PROVIDER', 1);

INSERT INTO JOBS(j_id, j_description, j_category, j_job_provided, j_provider_id, j_price, j_paused) VALUES
(1,'Trabajo en muebles de roble y pino','CARPINTERO','Arreglo de mesas y sillas',1,1500,false);

