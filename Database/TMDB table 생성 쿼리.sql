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
	overview varchar(1000), 
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
	overview VARCHAR(1000) NOT NULL,
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
	
