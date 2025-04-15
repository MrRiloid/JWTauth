use jwtauth;
ALTER TABLE users CHANGE password_hash password varchar(255);