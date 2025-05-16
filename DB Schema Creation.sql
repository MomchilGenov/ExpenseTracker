DROP DATABASE expense_tracker;
CREATE DATABASE expense_tracker;
USE expense_tracker;


CREATE TABLE users(
  id BIGINT AUTO_INCREMENT PRIMARY KEY ,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL, -- hashed password
  enabled BOOLEAN NOT NULL DEFAULT TRUE
);


CREATE TABLE roles(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL UNIQUE -- ROLE_USER,ROLES_ADMIN,...
);

DROP TABLE user_roles;
CREATE TABLE user_roles(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
role_id BIGINT NOT NULL,
FOREIGN KEY(user_id) REFERENCES users(id),
FOREIGN KEY(role_id) REFERENCES roles(id)
);

-- Saves categories for all users, duplicates are allowed, if a user needs a category removed,
-- the program will find the right ids below to be deleted via joins
DROP TABLE categories;
CREATE TABLE categories(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
user_id BIGINT,
FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE expenses(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
amount DOUBLE NOT NULL,
name VARCHAR(255) NOT NULL,
date DATE NOT NULL,
category_id BIGINT NOT NULL,
user_id BIGINT NOT NULL,
FOREIGN KEY(category_id) REFERENCES categories(id),
FOREIGN KEY(user_id) REFERENCES users(id)
);