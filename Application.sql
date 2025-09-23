CREATE DATABASE IF NOT EXISTS application;
-- 
USE application;
--
CREATE TABLE IF NOT EXISTS company
(
id INTEGER AUTO_INCREMENT,
companyname VARCHAR(50) NOT NULL,
PRIMARY KEY (id ASC),
CONSTRAINT companyname_unq UNIQUE (companyname),
CONSTRAINT companyname_chk CHECK (companyname REGEXP '^[A-Z ]+$')
);
--
CREATE TABLE IF NOT EXISTS department
(
id INTEGER AUTO_INCREMENT,
departmentname VARCHAR(50) NOT NULL,
companyid INTEGER NOT NULL,
PRIMARY KEY (id),
CONSTRAINT companyid_fk FOREIGN KEY (companyid) REFERENCES company (id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT departmentname_chk CHECK (departmentname REGEXP '^[A-Za-z ]+$')
);
--
CREATE TABLE IF NOT EXISTS task
(
id INTEGER AUTO_INCREMENT,
taskname VARCHAR(50) NOT NULL,
costtask /*DOUBLE*/ DECIMAL(13, 2) NOT NULL,
startdate DATE NOT NULL,
enddate DATE NOT NULL,
departmentid INTEGER NOT NULL,
netcosttask /*DOUBLE*/ DECIMAL(13, 2) GENERATED ALWAYS AS (costtask - (costtask * 0.257)) VIRTUAL,
daysoftask INTEGER GENERATED ALWAYS AS (DATEDIFF(enddate, startdate)) VIRTUAL,
monthssoftask INTEGER GENERATED ALWAYS AS (TIMESTAMPDIFF(MONTH, startdate, enddate)) VIRTUAL,
yearssoftask INTEGER GENERATED ALWAYS AS (TIMESTAMPDIFF(YEAR, startdate, enddate)) VIRTUAL,
PRIMARY KEY (id),
CONSTRAINT departmentid_fk_task FOREIGN KEY(departmentid) REFERENCES department (id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT costtask_chk CHECK (costtask >= 1000000.00),
CONSTRAINT startdate_enddate_chk CHECK (startdate <= enddate),
CONSTRAINT taskname_chk CHECK (taskname REGEXP '^[A-Za-z ]+$')
);
--
CREATE TABLE IF NOT EXISTS information
(
id INTEGER AUTO_INCREMENT,
firstname VARCHAR(50) NOT NULL,
lastname VARCHAR(50) NOT NULL,
age INTEGER NOT NULL,
sex CHARACTER(1) DEFAULT 'N',
datebirth DATE NOT NULL,
jobstatus BOOLEAN DEFAULT FALSE,
levelofeducation VARCHAR(50) DEFAULT 'N/A',
salary DOUBLE DEFAULT 0.0,
departmentid INTEGER,
taskid INTEGER,
netsalary DOUBLE GENERATED ALWAYS AS (salary - (salary * 0.257)) VIRTUAL,/*STORED,*/
PRIMARY KEY(id ASC, datebirth ASC),
INDEX datebirth_indx (datebirth ASC),
CONSTRAINT age_chk CHECK (age >= 18),
CONSTRAINT sex_chk CHECK (sex IN ('M', 'F', 'N')),
CONSTRAINT firstname_lastname_chk CHECK (firstname REGEXP '^[A-Za-z ]+$' AND lastname REGEXP '^[A-Za-z ]+$'),
CONSTRAINT salary_jobstatus_chk CHECK ((salary = 0.0 AND jobstatus = FALSE) OR (salary >= 1000.0 AND jobstatus = TRUE))
) PARTITION BY RANGE /*COLUMNS*/(YEAR(datebirth))
SUBPARTITION BY HASH (MONTH(datebirth))
(PARTITION p0 VALUES LESS THAN (1971)
(SUBPARTITION p0_dec,
SUBPARTITION p0_jan,
SUBPARTITION p0_feb,
SUBPARTITION p0_mar,
SUBPARTITION p0_apr,
SUBPARTITION p0_may,
SUBPARTITION p0_jun,
SUBPARTITION p0_jul,
SUBPARTITION p0_aug,
SUBPARTITION p0_sept,
SUBPARTITION p0_oct,
SUBPARTITION p0_nov),
PARTITION p1 VALUES LESS THAN (1989)
(SUBPARTITION p1_dec,
SUBPARTITION p1_jan,
SUBPARTITION p1_feb,
SUBPARTITION p1_mar,
SUBPARTITION p1_apr,
SUBPARTITION p1_may,
SUBPARTITION p1_jun,
SUBPARTITION p1_jul,
SUBPARTITION p1_aug,
SUBPARTITION p1_sept,
SUBPARTITION p1_oct,
SUBPARTITION p1_nov),
PARTITION p2 VALUES LESS THAN (2007)
(SUBPARTITION p2_dec,
SUBPARTITION p2_jan,
SUBPARTITION p2_feb,
SUBPARTITION p2_mar,
SUBPARTITION p2_apr,
SUBPARTITION p2_may,
SUBPARTITION p2_jun,
SUBPARTITION p2_jul,
SUBPARTITION p2_aug,
SUBPARTITION p2_sept,
SUBPARTITION p2_oct,
SUBPARTITION p2_nov),
PARTITION p3 VALUES LESS THAN (2025)
(SUBPARTITION p3_dec,
SUBPARTITION p3_jan,
SUBPARTITION p3_feb,
SUBPARTITION p3_mar,
SUBPARTITION p3_apr,
SUBPARTITION p3_may,
SUBPARTITION p3_jun,
SUBPARTITION p3_jul,
SUBPARTITION p3_aug,
SUBPARTITION p3_sept,
SUBPARTITION p3_oct,
SUBPARTITION p3_nov));
--
ALTER TABLE information REMOVE PARTITIONING; -- I have to remove partitioning cause MySQL doesnt support foreing keys.
--
ALTER TABLE information 
ADD CONSTRAINT departmentid_fk_information FOREIGN KEY (departmentid) REFERENCES department (id) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE information 
ADD CONSTRAINT taskid_fk FOREIGN KEY (taskid) REFERENCES task (id) ON DELETE SET NULL ON UPDATE CASCADE;
--
-- CREATE INDEX Datebirth_INDX ON Information (Datebirth ASC);
--
-- ALTER TABLE Information ADD INDEX Datebirth_INDX (Datebirth ASC);
--
DELIMITER //
CREATE TRIGGER Insert_Before_Company
/*AFTER*/BEFORE INSERT ON Company
FOR EACH ROW
BEGIN
IF (NEW.Companyname REGEXP '^[A-Za-z ]+$') THEN
SET NEW.Companyname = UPPER(NEW.Companyname);
END IF;
END;
// DELIMITER ;
--
DELIMITER //
CREATE TRIGGER Update_Before_Company
/*AFTER*/BEFORE UPDATE ON Company
FOR EACH ROW
BEGIN
IF (NEW.Companyname REGEXP '^[A-Za-z ]+$') THEN
SET NEW.Companyname = UPPER(NEW.Companyname);
END IF;
END;
// DELIMITER ;
--
DELIMITER //
CREATE TRIGGER Delete_After_Company
/*BEFORE*/AFTER DELETE ON Company
FOR EACH ROW
BEGIN
UPDATE Information SET Jobstatus = FALSE, Salary = 0.0, Taskid = NULL WHERE Departmentid IS NULL;
END;
// DELIMITER ;
--
DELIMITER //
CREATE TRIGGER Insert_Before_Department
/*AFTER*/BEFORE INSERT ON Department
FOR EACH ROW
BEGIN
IF (NEW.Departmentname REGEXP '^[A-Za-z ]+$') THEN
SET NEW.Departmentname = CONCAT(UPPER(SUBSTRING(NEW.Departmentname, 1, 1)), LOWER(SUBSTRING(NEW.Departmentname, 2, LENGTH(NEW.Departmentname) - 1)));
END IF;
END;
// DELIMITER ;
--
DELIMITER //
CREATE TRIGGER Update_Before_Department
/*AFTER*/BEFORE UPDATE ON Department
FOR EACH ROW
BEGIN
IF (NEW.Departmentname REGEXP '^[A-Za-z ]+$') THEN
SET NEW.Departmentname = CONCAT(UPPER(SUBSTRING(NEW.Departmentname, 1, 1)), LOWER(SUBSTRING(NEW.Departmentname, 2, LENGTH(NEW.Departmentname) - 1)));
END IF;
END;
// DELIMITER ;
--
DELIMITER //
CREATE TRIGGER Delete_After_Department
/*BEFORE*/AFTER DELETE ON Department
FOR EACH ROW
BEGIN
UPDATE Information SET Jobstatus = FALSE, Salary = 0.0, Taskid = NULL WHERE Departmentid IS NULL;
END;
// DELIMITER ;
--
DELIMITER //
CREATE TRIGGER Insert_Before_Task
/*AFTER*/BEFORE INSERT ON Task
FOR EACH ROW
BEGIN
IF (NEW.Taskname REGEXP '^[A-Za-z ]+$') THEN
SET NEW.Taskname = CONCAT(UPPER(SUBSTRING(NEW.Taskname, 1, 1)), LOWER(SUBSTRING(NEW.Taskname, 2, LENGTH(NEW.Taskname) - 1)));
END IF;
END;
// DELIMITER ;
--
DELIMITER //
CREATE TRIGGER Update_Before_Task
/*AFTER*/BEFORE UPDATE ON Task
FOR EACH ROW
BEGIN
IF (NEW.Taskname REGEXP '^[A-Za-z ]+$') THEN
SET NEW.Taskname = CONCAT(UPPER(SUBSTRING(NEW.Taskname, 1, 1)), LOWER(SUBSTRING(NEW.Taskname, 2, LENGTH(NEW.Taskname) - 1)));
END IF;
END;
// DELIMITER ;
--
DELIMITER //
CREATE TRIGGER Insert_Before_Information
/*AFTER*/BEFORE INSERT ON Information
FOR EACH ROW
BEGIN
IF (YEAR(CURRENT_DATE()) != NEW.Age + YEAR(NEW.Datebirth)) THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'Date birth is not valid with the age you gave.';
END IF;
IF (NEW.Firstname REGEXP '^[A-Za-z ]+$' AND NEW.Lastname REGEXP '^[A-Za-z ]+$') THEN
SET NEW.Firstname = CONCAT(UPPER(SUBSTRING(NEW.Firstname, 1, 1)), LOWER(SUBSTRING(NEW.Firstname, 2, LENGTH(NEW.Firstname) - 1)));
SET NEW.Lastname = CONCAT(UPPER(SUBSTRING(NEW.Lastname, 1, 1)), LOWER(SUBSTRING(NEW.Lastname, 2, LENGTH(NEW.Lastname) - 1)));
END IF;
IF (NEW.Levelofeducation = '4') THEN
SET NEW.Levelofeducation = 'Lyceum';
ELSEIF (NEW.Levelofeducation = '5') THEN
SET NEW.Levelofeducation = 'Institute of vocational training';
ELSEIF (NEW.Levelofeducation = '6') THEN
SET NEW.Levelofeducation = 'Bachelor''s degree';
ELSEIF (NEW.Levelofeducation = '7') THEN
SET NEW.Levelofeducation = 'Master''s degree';
ELSEIF (NEW.Levelofeducation = '8') THEN
SET NEW.Levelofeducation = 'PhD';
ELSEIF (NEW.Levelofeducation = 'N/A') THEN
SET NEW.Levelofeducation = 'No education';
/*ELSE 
SET NEW.Levelofeducation = 'No education';*/
END IF;
IF(NEW.Departmentid IS NULL AND NEW.Taskid IS NOT NULL) THEN
SET NEW.Taskid = NULL, NEW.Jobstatus = FALSE, NEW.Salary = 0.0;
ELSEIF (NEW.Departmentid IS NULL AND NEW.Taskid IS NULL) THEN
SET NEW.Jobstatus = FALSE, NEW.Salary = 0.0;
END IF;
IF (NEW.taskid != (SELECT Id FROM Task WHERE Departmentid = NEW.Departmentid) 
OR NEW.Departmentid != (SELECT Departmentid FROM Task WHERE Id = NEW.Taskid)) THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'Taskid and Departmentid must be together.';
END IF;
/*IF (NEW.Salary = 0.0) THEN
SET NEW.Jobstatus = FALSE;
ELSE
SET NEW.Jobstatus = TRUE;
END IF;*/
END;
// DELIMITER ;
--
DELIMITER //
CREATE TRIGGER Update_Before_Information
/*AFTER*/BEFORE UPDATE ON Information
FOR EACH ROW
BEGIN
IF (YEAR(CURRENT_DATE()) != NEW.Age + YEAR(NEW.Datebirth)) /*OR (YEAR(CURRENT_DATE()) != NEW.Age + YEAR(OLD.Datebirth) AND NEW.Datebirth = OLD.Datebirth)*/ THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'Date birth is not valid with the age you gave.';
END IF;
IF (NEW.Firstname REGEXP '^[A-Za-z ]+$' AND NEW.Lastname REGEXP '^[A-Za-z ]+$') THEN
SET NEW.Firstname = CONCAT(UPPER(SUBSTRING(NEW.Firstname, 1, 1)), LOWER(SUBSTRING(NEW.Firstname, 2, LENGTH(NEW.Firstname) - 1)));
SET NEW.Lastname = CONCAT(UPPER(SUBSTRING(NEW.Lastname, 1, 1)), LOWER(SUBSTRING(NEW.Lastname, 2, LENGTH(NEW.Lastname) - 1)));
END IF;
IF (NEW.Levelofeducation = '4') THEN
SET NEW.Levelofeducation = 'Lyceum';
ELSEIF (NEW.Levelofeducation = '5') THEN
SET NEW.Levelofeducation = 'Institute of vocational training';
ELSEIF (NEW.Levelofeducation = '6') THEN
SET NEW.Levelofeducation = 'Bachelor''s degree';
ELSEIF (NEW.Levelofeducation = '7') THEN
SET NEW.Levelofeducation = 'Master''s degree';
ELSEIF (NEW.Levelofeducation = '8') THEN
SET NEW.Levelofeducation = 'PhD';
ELSEIF (NEW.Levelofeducation = 'N/A') THEN
SET NEW.Levelofeducation = 'No education';
/*ELSE 
SET NEW.Levelofeducation = 'No education';*/
END IF;
IF (NEW.Departmentid IS NOT NULL) THEN
SET NEW.Jobstatus = TRUE, NEW.Salary = NEW.Salary;
END IF;
IF(NEW.Departmentid IS NULL AND NEW.Taskid IS NOT NULL) THEN
SET NEW.Taskid = NULL, NEW.Jobstatus = FALSE, NEW.Salary = 0.0;
ELSEIF (NEW.Departmentid IS NULL AND NEW.Taskid IS NULL) THEN
SET NEW.Jobstatus = FALSE, NEW.Salary = 0.0;
END IF;
IF (NEW.taskid != (SELECT Id FROM Task WHERE Departmentid = NEW.Departmentid) 
AND NEW.Departmentid != (SELECT Departmentid FROM Task WHERE Id = NEW.Taskid)) THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'Taskid and Departmentid must be together.';
END IF;
/*IF (NEW.Salary = 0.0) THEN
SET NEW.Jobstatus = FALSE;
ELSE
SET NEW.Jobstatus = TRUE;
END IF;*/
END;
// DELIMITER ;
--
INSERT INTO Company VALUES (1, 'Jasmine');
INSERT INTO Company (Companyname) VALUES ('Barrock');
INSERT INTO Company SET Companyname = 'Central';
COMMIT;
--
INSERT INTO Department VALUES (1, 'Department of computer engineers', 1);
INSERT INTO Department (Departmentname, Companyid) VALUES ('Department of civil engineers', 2);
INSERT INTO Department SET Departmentname = 'Department of agriculture engineers', Companyid = 3;
COMMIT;
--
INSERT INTO Task (Taskname, Costtask, Startdate, Enddate, Departmentid) 
VALUES ('CoDIng', 10000000000.00, '2015-01-01', '2019-12-31', 1);
INSERT INTO Task(Taskname, Costtask, Startdate, Enddate, Departmentid) 
VALUES ('ConstrUCtioN', 1000000000.00, '2020-02-10', '2025-11-28', 2);
INSERT INTO Task SET Taskname = 'important Task', Costtask = 5500000.00, Startdate = '2030-05-10', Enddate = '2035-10-20', 
Departmentid = 3;
COMMIT;
--
INSERT INTO Information(Id, Firstname, Lastname, Age, Sex, Datebirth, Jobstatus, Salary, Departmentid, Taskid) 
VALUES (1, 'Panagiotis', 'Chronopoulos', 28, 'M', '1997-03-27', TRUE, 1000.0, 1, 1);
INSERT INTO Information(Firstname, Lastname, Age, Datebirth) VALUES ('nikos', 'stergiou', 28, '1997-02-17');
INSERT INTO Information(Firstname, Lastname, Age, Sex, Datebirth, Jobstatus, Salary, Levelofeducation, Departmentid, Taskid) VALUES ('Popi', 'theofanopoulou', 30, 'F', '1995-04-15', TRUE, 1000.0, '4', 1, 1);
INSERT INTO Information(Firstname, Lastname, Age, Sex, Datebirth, Jobstatus, Salary, Levelofeducation, Departmentid, Taskid) VALUES ('Makis', 'arvanitis', 40, 'M', '1985-05-21', TRUE, 2000.0, '5', 1, 1);
INSERT INTO Information(Firstname, Lastname, Age, Sex, Datebirth, Jobstatus, Salary, Levelofeducation, Departmentid, Taskid) VALUES ('Giwrgos', 'maggos', 27, 'M', '1998-10-05', TRUE, 3000.0, '6', 2, 2);
INSERT INTO Information(Firstname, Lastname, Age, Sex, Datebirth, Jobstatus, Salary, Levelofeducation, Departmentid, Taskid) VALUES ('alexandros', 'Spyropoulos', 30, 'M', '1995-04-19', TRUE, 2500.0, '7', 2, 2);
INSERT INTO Information(Firstname, Lastname, Age, Sex, Datebirth, Jobstatus, Salary, Levelofeducation, Departmentid, Taskid) VALUES ('niki', 'alexiou', 33, 'F', '1992-04-19', TRUE, 1500.0, '8', 3, 3);
COMMIT;
--
SET GLOBAL event_scheduler = ON;
--
DELIMITER //
CREATE EVENT IF NOT EXISTS Updateage
ON SCHEDULE -- AT NOW() + INTERVAL 1 MINUTE
EVERY 1 YEAR STARTS '2025-01-01 00:00:00'
DO
BEGIN
UPDATE Information SET Age = Age + 1; -- , Datebirth = DATE_SUB(Datebirth, INTERVAL 1 YEAR);
COMMIT;
END;
// DELIMITER ;
--
DELIMITER //
CREATE EVENT IF NOT EXISTS Addpartitions
ON SCHEDULE -- EVERY 1 MINUTE STARTS CURRENT_TIMESTAMP + INTERVAL 1 MINUTE -- AT NOW() + INTERVAL 1 MINUTE
EVERY 18 YEAR STARTS '2025-01-01 00:00:00'
DO
BEGIN
DECLARE Currentyear, Counter INTEGER;
DECLARE Sqlstatement TEXT;
DECLARE Partitioname TEXT;
SET Counter = (SELECT SUBSTRING(PARTITION_NAME, 2) AS Counter
FROM information_schema.PARTITIONS
WHERE TABLE_SCHEMA = 'Application'
ORDER BY PARTITION_DESCRIPTION DESC
LIMIT 1) + 1;
SET Currentyear = (SELECT CAST(PARTITION_DESCRIPTION AS UNSIGNED) AS Currentyear 
FROM information_schema.PARTITIONS
WHERE TABLE_SCHEMA = 'Application'
ORDER BY PARTITION_DESCRIPTION DESC
LIMIT 1) + 18;
SET Partitioname = CONCAT('p', Counter);
SET @Sqlstatement = CONCAT(
'ALTER TABLE Information ADD PARTITION (PARTITION ', Partitioname, ' VALUES LESS THAN (', Currentyear, ')',
'(SUBPARTITION p', Counter, '_dec,',
'SUBPARTITION p', Counter, '_jan,',
'SUBPARTITION p', Counter, '_feb,',
'SUBPARTITION p', Counter, '_mar,',
'SUBPARTITION p', Counter, '_apr,',
'SUBPARTITION p', Counter, '_may,',
'SUBPARTITION p', Counter, '_jun,',
'SUBPARTITION p', Counter, '_jul,',
'SUBPARTITION p', Counter, '_aug,',
'SUBPARTITION p', Counter, '_sept,',
'SUBPARTITION p', Counter, '_oct,',
'SUBPARTITION p', Counter, '_nov));');
PREPARE statement FROM @Sqlstatement;
EXECUTE statement;
DEALLOCATE PREPARE statement;

/*ALTER TABLE Information ADD PARTITION (PARTITION p4 VALUES LESS THAN (2025)
(SUBPARTITION p4_dec,
SUBPARTITION p4_jan,
SUBPARTITION p4_feb,
SUBPARTITION p4_mar,
SUBPARTITION p4_apr,
SUBPARTITION p4_may,
SUBPARTITION p4_jun,
SUBPARTITION p4_jul,
SUBPARTITION p4_aug,
SUBPARTITION p4_sept,
SUBPARTITION p4_oct,
SUBPARTITION p4_nov));*/

/*SET Currentyear = Currentyear + 18;
SET Counter = Counter + 1;*/
END;
// DELIMITER ;
--
/*SELECT PARTITION_NAME, SUBSTRING(PARTITION_NAME, 2) AS Counter, CAST(PARTITION_DESCRIPTION AS UNSIGNED) AS Currentyear
FROM information_schema.PARTITIONS
WHERE TABLE_SCHEMA = 'Application'
ORDER BY PARTITION_DESCRIPTION DESC
LIMIT 1;
--
SHOW EVENTS;
--
SELECT TABLE_SCHEMA, TABLE_NAME, PARTITION_NAME, PARTITION_METHOD, PARTITION_EXPRESSION FROM INFORMATION_SCHEMA.PARTITIONS WHERE
TABLE_SCHEMA = 'Application' AND TABLE_NAME = 'Information' ORDER BY PARTITION_ORDINAL_POSITION;
--
SHOW CREATE TABLE Information;*/
--
SELECT * FROM Company ORDER BY Id ASC;
SELECT * FROM Department ORDER BY Id ASC;
SELECT * FROM Task ORDER BY Id ASC;
SELECT * FROM Information ORDER BY Id ASC;
--
/*UPDATE company SET id = 3 WHERE id = 1;
COMMIT;*/
--
DELIMITER //
CREATE PROCEDURE Company()
BEGIN
SELECT * FROM Company ORDER BY Id ASC;
END;
// DELIMITER ;
--
DELIMITER //
CREATE PROCEDURE Department()
BEGIN
SELECT * FROM Department ORDER BY Id ASC;
END;
// DELIMITER ;
--
DELIMITER //
CREATE PROCEDURE Task()
BEGIN
SELECT * FROM Task ORDER BY Id ASC;
END;
// DELIMITER ;
--
DELIMITER //
CREATE PROCEDURE Information()
BEGIN
SELECT * FROM Information ORDER BY Id ASC;
END;
// DELIMITER ;
--
DELIMITER //
CREATE PROCEDURE autoincrementcompany()
BEGIN
DECLARE number INTEGER;
SELECT auto_increment INTO number FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'application' AND table_name = 'company';
SET @sql = CONCAT('ALTER TABLE company AUTO_INCREMENT = ', (number - 1));
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
END;
// DELIMITER ;
--
DELIMITER //
CREATE PROCEDURE autoincrementdepartment()
BEGIN
DECLARE number INTEGER;
SELECT auto_increment INTO number FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'application' AND table_name = 'department';
SET @sql = CONCAT('ALTER TABLE department AUTO_INCREMENT = ', (number - 1));
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
END;
// DELIMITER ;
--
DELIMITER //
CREATE PROCEDURE autoincrementtask()
BEGIN
DECLARE number INTEGER;
SELECT auto_increment INTO number FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'application' AND table_name = 'task';
SET @sql = CONCAT('ALTER TABLE task AUTO_INCREMENT = ', (number - 1));
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
END;
// DELIMITER ;
--
DELIMITER //
CREATE PROCEDURE autoincrementinformation()
BEGIN
DECLARE number INTEGER;
SELECT auto_increment INTO number FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'application' AND table_name = 'information';
SET @sql = CONCAT('ALTER TABLE information AUTO_INCREMENT = ', (number - 1));
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
END;
// DELIMITER ;
--
CALL Company();
CALL Department();
CALL Task();
CALL Information();
CALL autoincrementcompany();
CALL autoincrementdepartment();
CALL autoincrementtask();
CALL autoincrementinformation();
--
--
--
--
--
USE application;
--
DROP PROCEDURE IF EXISTS autoincrementinformation;
--
DROP PROCEDURE IF EXISTS autoincrementtask;
--
DROP PROCEDURE IF EXISTS autoincrementdepartment;
--
DROP PROCEDURE IF EXISTS autoincrementcompany;
--
DROP PROCEDURE IF EXISTS Information;
--
DROP PROCEDURE IF EXISTS Task;
--
DROP PROCEDURE IF EXISTS Department;
--
DROP PROCEDURE IF EXISTS Company;
--
ALTER EVENT Addpartitions ENABLE;
--
ALTER EVENT Addpartitions DISABLE;
--
DROP EVENT IF EXISTS Addpartitions;
--
ALTER EVENT Updateage ENABLE;
--
ALTER EVENT Updateage DISABLE;
--
DROP EVENT IF EXISTS Updateage;
--
SET GLOBAL event_scheduler = OFF;
--
DROP TRIGGER IF EXISTS Update_Before_Information;
--
DROP TRIGGER IF EXISTS Insert_Before_Information;
--
DROP TRIGGER IF EXISTS Update_Before_Task;
--
DROP TRIGGER IF EXISTS Insert_Before_Task;
--
DROP TRIGGER IF EXISTS Delete_After_Department;
--
DROP TRIGGER IF EXISTS Update_Before_Department;
--
DROP TRIGGER IF EXISTS Insert_Before_Department;
--
DROP TRIGGER IF EXISTS Delete_After_Company;
--
DROP TRIGGER IF EXISTS Update_Before_Company;
--
DROP TRIGGER IF EXISTS Insert_Before_Company;
--
DELETE FROM information;
COMMIT;
--
TRUNCATE TABLE information;
--
ALTER TABLE information DROP CONSTRAINT taskid_fk;
--
ALTER TABLE information DROP CONSTRAINT departmentid_fk_information;
--
ALTER TABLE information DROP CONSTRAINT salary_jobstatus_chk;
--
ALTER TABLE information DROP CONSTRAINT firstname_lastname_chk;
--
ALTER TABLE information DROP CONSTRAINT sex_chk;
--
ALTER TABLE information DROP CONSTRAINT age_chk;
--
ALTER TABLE information MODIFY id INTEGER NOT NULL;
--
ALTER TABLE information DROP INDEX datebirth_indx;
--
ALTER TABLE information DROP PRIMARY KEY;
--
DROP TABLE IF EXISTS information;
--
DELETE FROM task;
COMMIT;
--
TRUNCATE TABLE task;
--
ALTER TABLE task MODIFY id INTEGER NOT NULL;
--
ALTER TABLE task DROP CONSTRAINT startdate_enddate_chk;
--
ALTER TABLE task DROP CONSTRAINT costtask_chk;
--
ALTER TABLE task DROP CONSTRAINT departmentid_fk_task;
--
ALTER TABLE task DROP PRIMARY KEY;
--
DROP TABLE IF EXISTS task;
--
DELETE FROM department;
COMMIT;
--
TRUNCATE TABLE department;
--
ALTER TABLE department MODIFY id INTEGER NOT NULL;
--
ALTER TABLE department DROP CONSTRAINT departmentname_chk;
--
ALTER TABLE department DROP CONSTRAINT companyid_fk;
--
ALTER TABLE department DROP PRIMARY KEY;
--
DROP TABLE IF EXISTS department;
--
DELETE FROM company;
COMMIT;
--
TRUNCATE TABLE company;
--
ALTER TABLE company MODIFY id INTEGER NOT NULL;
--
ALTER TABLE company DROP CONSTRAINT companyname_chk;
--
ALTER TABLE company DROP CONSTRAINT companyname_unq;
--
ALTER TABLE company DROP PRIMARY KEY;
--
DROP TABLE IF EXISTS company;
--
DROP DATABASE IF EXISTS application;