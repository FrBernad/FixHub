TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
insert into USERS(u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES ('password','name','surname','email','phoneNumber',null,null,'state','city');