TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE follows RESTART IDENTITY AND COMMIT NO CHECK;

insert into USERS(u_id,u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES (1,'password', 'Ignacio', 'Lopez', 'ignacio@yopmail.com', '5491112345678', null, null, 'Caballito', 'CABA');


insert into USERS(u_id,u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES (2,'password', 'Raul', 'Perez', 'raul@yopmail.com', '5491187654321', null, null, 'Recoleta', 'CABA');

insert into follows(f_user_id, f_followed_user_id) values(1, 2);
insert into follows(f_user_id, f_followed_user_id) values(2, 1);