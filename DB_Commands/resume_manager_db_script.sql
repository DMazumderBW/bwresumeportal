DROP DATABASE IF EXISTS resume_portal_db;

CREATE DATABASE resume_portal_db;

USE resume_portal_db;

DROP TABLE IF EXISTS skill;

CREATE TABLE skill (
  skill_id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  PRIMARY KEY (skill_id)
) ENGINE=INNODB;

DROP TABLE IF EXISTS user;

CREATE TABLE user (
  user_key BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id VARCHAR(50) NOT NULL,
  password VARCHAR(10) NOT NULL,
  first_name VARCHAR(200) NOT NULL,
  middle_name VARCHAR(200),
  last_name VARCHAR(200) NOT NULL,
  PRIMARY KEY (user_key)
)ENGINE=INNODB;

DROP TABLE IF EXISTS resume;

CREATE TABLE resume (
  resume_id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  file_path VARCHAR(200) NOT NULL,
  upload_timestamp TIMESTAMP NOT NULL,
  user_key BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (resume_id),
  FOREIGN KEY (user_key) REFERENCES user(user_key)
  ON UPDATE CASCADE ON DELETE RESTRICT
)ENGINE=INNODB;

DROP TABLE IF EXISTS resume_skill;

CREATE TABLE resume_skill (
  skill_id BIGINT(20) UNSIGNED NOT NULL,
  resume_id BIGINT(20) UNSIGNED NOT NULL,
  FOREIGN KEY (skill_id) REFERENCES skill(skill_id)
  ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (resume_id) REFERENCES resume(resume_id)
  ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=INNODB;


INSERT INTO resume_portal_db.skill(name) VALUES
('Java'),
('J2EE'),
('Hibernate'),
('Spring'),
('Restful'),
('SOAP'),
('JavaScript'),
('Nodejs'),
('Angularjs'),
('Bootstrap'),
('APIGEE'),
('HTML5'),
('SQL'),
('PL/SQL'),
('MYSQL'),
('AWS'),
('Hadoop'),
('Cassandra'),
('Pig'),
('Hive'),
('Big Data'),
('Scala'),
('Spark'),
('Python'),
('Shellscripting'),
('Android'),
('Object-C'),
('Ab Initio'),
('ER Studio'),
('Tableau'),
('PHP'),
('Kafka'),
('Selenium'),
('SOA'),
('Jmeter'),
('Devtest'),
('ETL'),
('Docker'),
('Gitlab'),
('.NET'),
('BA'),
('Data Warehousing'),
('QA Automation'),
('GCF'),
('Cloud Foundry'),
('PCF'),
('Java Microservices'),
('Machine learning'),
('R'),
('SharePoint'),
('Oracle'),
('Teradata'),
('Linux'),
('Perl'),
('Business Analysis'),
('Data Analysis'),
('Snaplogic'),
('Manual '),
('Testing'),
('Automation'),
('Project Management'),
('Networking'),
('Cisco'),
('System Administration'),
('Hyper V') ;


#INSERT INTO user(user_id,first_name,middle_name,last_name) VALUES ('dummyid','dummyfirst','dummymiddle','dummylast');

#Soft Delete - Added new field soft_delete with allowed values 'YES' and default value 'NO'
#Updated by Aditya More
ALTER TABLE `resume_portal_db`.`resume`
ADD COLUMN `soft_delete` VARCHAR(45) NULL DEFAULT 'NO' AFTER `user_key`;
ALTER TABLE `resume_portal_db`.`resume`
ADD COLUMN `delete_timestamp` DATE NULL AFTER `soft_delete`;

#Skill Matrix Modification
#Edited by Debkanya <Debkanya.Mazumder@bitwiseglobal.com> and Aditya <Aditya.More@bitwiseglobal.com>
#technical
insert into skill(name)  values('K2View');
insert into skill(name)  values('Data modeler');
insert into skill(name)  values('Essbase');
insert into skill(name)  values('ROR');
insert into skill(name)  values('Web dev with CodeIgniter');
insert into skill(name)  values('IBM Content Manager');
insert into skill(name)  values('Mainframe');
insert into skill(name)  values('Talend');
insert into skill(name)  values('Hadoop admin');
insert into skill(name)  values('Hadoop developer');
insert into skill(name)  values('ETL tester');

#non-technical
insert into skill(name)  values('Client Principal');
insert into skill(name)  values('Recruiter');
insert into skill(name)  values('Office admin');
insert into skill(name)  values('Benefits Coordinator');
insert into skill(name)  values('HR Coordinator');

CREATE USER 'bwuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL ON resume_portal_db.* TO 'bitwise'@'localhost';
