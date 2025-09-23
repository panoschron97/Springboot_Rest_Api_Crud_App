CREATE DATABASE IF NOT EXISTS application;
--
USE application;
--
CREATE TABLE IF NOT EXISTS users
 (
username VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
enabled TINYINT NOT NULL,
PRIMARY KEY (username ASC)
);
--
INSERT INTO users 
VALUES 
/*('alexis','{noop}test123',1),
('nikos','{noop}test123',1),
('panos','{noop}test123',1);*/
('alexis','{bcrypt}$2a$10$ulDvfyoQZHdz6XsW5up./uC8oW1HWZmqPnwAfVoLx3gkRwtBkk4.G',1),-- password
('nikos','{bcrypt}$2a$10$ulDvfyoQZHdz6XsW5up./uC8oW1HWZmqPnwAfVoLx3gkRwtBkk4.G',1),-- password
('panos','{bcrypt}$2a$10$ulDvfyoQZHdz6XsW5up./uC8oW1HWZmqPnwAfVoLx3gkRwtBkk4.G',1);-- password
COMMIT;
-- https://www.bcryptcalculator.com/
CREATE TABLE IF NOT EXISTS authorities
(
username VARCHAR(100) NOT NULL,
authority VARCHAR(100) NOT NULL,
-- UNIQUE KEY username_authority_UNQ (username ASC, authority ASC),
CONSTRAINT username_authority_UNQ UNIQUE(username ASC, authority ASC),
CONSTRAINT username_FK FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE ON UPDATE CASCADE
);
--
INSERT INTO authorities
VALUES 
('alexis','ROLE_EMPLOYEE'),
('nikos','ROLE_EMPLOYEE'),
('nikos','ROLE_MANAGER'),
('panos','ROLE_EMPLOYEE'),
('panos','ROLE_MANAGER'),
('panos','ROLE_ADMIN');
COMMIT;
--
SELECT * FROM users;
SELECT * FROM authorities;
--
/*DELETE FROM users WHERE username='john';
COMMIT;*/
--
SELECT * FROM users;
SELECT * FROM authorities;
--
--
--
--
--
USE application;
--
DELETE FROM authorities;
COMMIT;
--
TRUNCATE TABLE authorities;
--
ALTER TABLE authorities DROP CONSTRAINT username_FK;
--
ALTER TABLE authorities DROP CONSTRAINT username_authority_UNQ;
--
DROP TABLE IF EXISTS authorities;
--
DELETE FROM users;
COMMIT;
--
TRUNCATE TABLE users;
--
ALTER TABLE users DROP PRIMARY KEY;
--
DROP TABLE IF EXISTS users;
--
DROP DATABASE IF EXISTS application;