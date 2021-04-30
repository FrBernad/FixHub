TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE jobs RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE job_image RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO users(u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES ('123456','Pedro','De La Fuentes','Pedro123@yopmail.com','1587654321',null,null,'Rosario','Santa Fe');