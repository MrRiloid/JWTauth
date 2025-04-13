CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    password_hash VARCHAR(255),
    role VARCHAR(30)
);

CREATE TABLE password_history (
    id INT PRIMARY KEY AUTO_INCREMENT,
    old_password_hash VARCHAR(255),
    new_password_hash VARCHAR(255),
    FOREIGN KEY (id) references users(id)
);