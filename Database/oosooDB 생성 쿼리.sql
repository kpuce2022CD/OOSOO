CREATE DATABASE oosooDB DEFAULT CHARACTER SET UTF8 COLLATE UTF8_general_ci;

USE oosooDB;

CREATE TABLE contents(
	id VARCHAR(100) NOT NULL PRIMARY KEY, 
	_type VARCHAR(10) NOT NULL,
	title VARCHAR(100) NOT NULL, 
	genre VARCHAR(100) NOT NULL, 
	production_countries VARCHAR(200) NOT NULL, 
	vote_count INT NOT NULL, 
	vote_average FLOAT(3,1) NOT NULL, 
	number_of_seasons INT,
	number_of_episodes INT,
	release_date DATE, 
	adult BOOLEAN, 
	poster_path VARCHAR(200), 
	runtime INT, 
	overview VARCHAR(2000), 
	now_status VARCHAR(100),
	flatrate VARCHAR(100), 
	rent VARCHAR(100), 
	buy VARCHAR(100)
);

CREATE TABLE users(
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	email VARCHAR(50) NOT NULL,
	passwd VARCHAR(20) NOT NULL,
	name VARCHAR(20) NOT NULL,
	phone_number VARCHAR(20) NOT NULL,
	nickname VARCHAR(20) NOT NULL,
	gender BOOLEAN NOT NULL,
	birthday VARCHAR(10),
	job VARCHAR(20),
	overview VARCHAR(200),
	coin INT
);

CREATE TABLE user_interworking(
	i_id VARCHAR(50) NOT NULL PRIMARY KEY,
	u_id INT NOT NULL,
	platform VARCHAR(20) NOT NULL,
	id VARCHAR(50) NOT NULL,
	passwd VARCHAR(20) NOT NULL,
	profile_name VARCHAR(20) NOT NULL,
	expiration_date DATE,
	simple_login VARCHAR(20),
	FOREIGN KEY(u_id) REFERENCES users(id)
);

CREATE TABLE contents_review(
	id INT NOT NULL PRIMARY KEY,
	c_id varchar(100) NOT NULL,
	u_id INT NOT NULL,
	_like BOOLEAN NOT NULL,
	rating FLOAT(3,1) NOT NULL,
	review VARCHAR(1000) NOT NULL,
	_datetime DATETIME NOT NULL,
	FOREIGN KEY(c_id) REFERENCES contents(id),
	FOREIGN KEY(u_id) REFERENCES users(id)
);

CREATE TABLE contents_seasons(
	_id VARCHAR(100) NOT NULL PRIMARY KEY,
	c_id VARCHAR(100) NOT NULL,
	air_date DATE NOT NULL,
	title VARCHAR(100) NOT NULL,
	NUMBER INT NOT NULL,
	overview VARCHAR(2000),
	poster_path VARCHAR(200),
	FOREIGN KEY(c_id) REFERENCES contents(id)
);

CREATE TABLE contents_episodes(
	_id VARCHAR(100) NOT NULL PRIMARY KEY,
	c_id VARCHAR(100) NOT NULL,
	season_num INT NOT NULL,
	title VARCHAR(100) NOT NULL,
	NUMBER INT NOT NULL,
	air_date DATE,
	vote_count INT,
	vote_average FLOAT(3, 1),
	overview VARCHAR(2000),
	still_path VARCHAR(200),
	crew VARCHAR(1000),
	guests VARCHAR(1000),
	FOREIGN KEY(c_id) REFERENCES contents(id)
);

CREATE TABLE wish_list(
	id VARCHAR(100) NOT NULL PRIMARY KEY,
	c_id VARCHAR(100) NOT NULL,
	i_id VARCHAR(50) NOT NULL,
	FOREIGN KEY(c_id) REFERENCES contents(id),
	FOREIGN KEY(i_id) REFERENCES user_interworking(i_id)
);

CREATE TABLE watching_log(
	id VARCHAR(100) NOT NULL PRIMARY KEY,
	c_id VARCHAR(100) NOT NULL,
	i_id VARCHAR(50) NOT NULL,
	FOREIGN KEY(c_id) REFERENCES contents(id),
	FOREIGN KEY(i_id) REFERENCES user_interworking(i_id)
);