ALTER TABLE password_history DROP FOREIGH KEY password_history_ibfk_1;
ALTER TABLE password_history ADD COLUMN user_id INT;
ALTER TABLE password_history ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users(id);