TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
insert into USERS(password, name, surname, email, phone_number, state, city)
VALUES ('password', 'name', 'surname', 'email', 'phoneNumber', 'state', 'city');