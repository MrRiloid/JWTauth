ALTER TABLE password_history CHANGE oldPasswordHash old_password_hash varchar(255);
ALTER TABLE password_history CHANGE newPasswordHash new_password_hash varchar(255);
