TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE notifications RESTART IDENTITY AND COMMIT NO CHECK;

-- CLIENT
insert into USERS(u_id, u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES (1, 'password', 'Raul', 'Perez', 'raul@yopmail.com', '5491187654321', null, null, 'Recoleta', 'CABA');


-- CLIENT_NO_NOT
insert into USERS(u_id, u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES (2, 'password', 'Pedro', 'Romero', 'pedro@yopmail.com', '5491109876543', null, null, 'Once', 'CABA');

insert into ROLES(r_role, r_user_id)
VALUES ('USER', 1);
insert into ROLES(r_role, r_user_id)
VALUES ('VERIFIED', 1);

insert into ROLES(r_role, r_user_id)
VALUES ('USER', 2);
insert into ROLES(r_role, r_user_id)
VALUES ('VERIFIED', 2);

INSERT INTO NOTIFICATIONS(n_id, n_date, n_resource, n_seen, n_type, n_user_id)
VALUES (1, '2021-04-06', 1, true, 'JOB_REQUEST', 1);
INSERT INTO NOTIFICATIONS(n_id, n_date, n_resource, n_seen, n_type, n_user_id)
VALUES (2, '2021-04-05', 1, false, 'JOB_REQUEST', 1);

