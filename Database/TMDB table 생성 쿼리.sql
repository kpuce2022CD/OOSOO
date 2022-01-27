create schema tmdb default character set UTF8;

USE tmdb;

create table contents_movie (
	id int not null PRIMARY KEY, 
	title varchar(100) not null, 
	genre varchar(100) not null, 
	production_countries varchar(200) not null, 
	release_date date not null, 
	adult boolean not null, 
	vote_average float(3,1) not null, 
	vote_count int not null, 
	homepage varchar(200), 
	poster_path varchar(200), 
	runtime int, 
	overview VARCHAR(2000), 
	flatrate varchar(100), 
	rent varchar(100), 
	buy varchar(100)
);

CREATE TABLE contents_tv(
	id INT NOT NULL PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	genre varchar(100) NOT NULL, 
	production_countries varchar(200) NOT NULL,
	vote_average float(3,1) not null, 
	vote_count int not null, 
	overview VARCHAR(2000) NOT NULL,
	homepage VARCHAR(200) NOT NULL,
	number_of_seasons INT NOT NULL,
	number_of_episodes INT NOT NULL,
	now_status VARCHAR(100) NOT NULL,
	first_air_date DATE,
	last_air_date DATE,
	poster_path varchar(200), 
	flatrate varchar(100), 
	rent varchar(100), 
	buy varchar(100)
);


CREATE TABLE users(
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
email VARCHAR(50) NOT NULL,
passwd VARCHAR(20) NOT NULL,
NAME VARCHAR(20) NOT NULL,
phone_number VARCHAR(20) NOT NULL,
nickname VARCHAR(20) NOT NULL,
gender BOOLEAN NOT NULL,
age INT NOT NULL,
job VARCHAR(20),
overview VARCHAR(200),
coin INT
);

CREATE TABLE user_interworking(
i_id VARCHAR(50) NOT NULL PRIMARY KEY,
id VARCHAR(50) NOT NULL,
passwd VARCHAR(20) NOT NULL,
profile_name VARCHAR(20) NOT NULL,
expiration_date DATE NOT NULL,
simple_login VARCHAR(20)
);

CREATE TABLE movie_review(
id INT NOT NULL PRIMARY KEY,
m_id INT NOT NULL,
u_id INT NOT NULL,
_like BOOLEAN NOT NULL,
rating FLOAT(3,1) NOT NULL,
review VARCHAR(1000) NOT NULL,
_datetime DATETIME NOT NULL,
FOREIGN KEY(m_id) REFERENCES contents_movie(id),
FOREIGN KEY(u_id) REFERENCES users(id)
);

CREATE TABLE tv_review(
id INT NOT NULL PRIMARY KEY,
t_id INT NOT NULL,
u_id INT NOT NULL,
_like BOOLEAN NOT NULL,
rating FLOAT(3,1) NOT NULL,
review VARCHAR(1000) NOT NULL,
_datetime DATETIME NOT NULL,
FOREIGN KEY(t_id) REFERENCES contents_tv(id),
FOREIGN KEY(u_id) REFERENCES users(id)
);

	

