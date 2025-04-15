CREATE TABLE password_history (
    id INT PRIMARY KEY AUTO_INCREMENT,
    oldPasswordHash VARCHAR(255),
    newPasswordHash VARCHAR(255),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);