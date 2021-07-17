TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE session_refresh_tokens RESTART IDENTITY AND COMMIT NO CHECK;

insert into USERS(u_id, u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES (1, 'password', 'Ignacio', 'Lopez', 'ignacio@yopmail.com', '5491112345678', null, null, 'Caballito', 'CABA');

insert into SESSION_REFRESH_TOKENS(srt_id, srt_expiration_date, srt_token, srt_user_id)
VALUES (1, '2022-05-04 18:04:30.843000', '83feb0af-f467-4374-91fb-8f96db3f9a23', 1);