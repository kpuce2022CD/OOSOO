CREATE SCHEMA oosoo DEFAULT CHARACTER SET UTF8;

USE oosoo;

CREATE TABLE users(
	u_id VARCHAR(50) NOT NULL PRIMARY KEY,
	u_passwd VARCHAR(20) NOT NULL,
	u_name VARCHAR(20) NOT NULL,
	u_nickname VARCHAR(20) NOT NULL,
	u_gender BOOLEAN NOT NULL,
	u_age INT NOT NULL,
	u_subs VARCHAR(100),
	u_information VARCHAR(100)
	);
	
CREATE TABLE contents(
	c_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	c_genre VARCHAR(20) NOT NULL,
	c_name VARCHAR(100) NOT NULL,
	c_release_date DATE NOT NULL,
	c_cast VARCHAR(200) NOT NULL,
	c_platform VARCHAR(100) NOT NULL,
	c_information TEXT(1000) NOT NULL,
	c_preview_url VARCHAR(200),
	c_number INT,
	c_age_limit VARCHAR(20),
	c_rating FLOAT(3, 1)
	);
	
CREATE TABLE contents_review(
	cr_id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
	cr_c_id INT NOT NULL,
	cr_u_id VARCHAR(50) NOT NULL,
	cr_like BOOLEAN NOT NULL,
	cr_rating FLOAT(3, 1) NOT NULL,
	cr_review TEXT(1000) NOT NULL,
	FOREIGN KEY(cr_c_id) REFERENCES contents(c_id),
	FOREIGN KEY(cr_u_id) REFERENCES users(u_id)
	);

CREATE TABLE ranking(
	r_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	r_c_id INT NOT NULL,
	r_change INT,
	FOREIGN KEY(r_c_id) REFERENCES contents(c_id)
	);
	
CREATE TABLE interworking(
	i_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	i_u_id VARCHAR(50) NOT NULL,
	i_c_id INT NOT NULL,
	i_ott_name VARCHAR(20) NOT NULL,
	i_ott_id VARCHAR(20) NOT NULL,
	i_ott_passwd VARCHAR(20) NOT NULL,
	i_ott_profile VARCHAR(20) NOT NULL,
	FOREIGN KEY(i_c_id) REFERENCES contents(c_id),
	FOREIGN KEY(i_u_id) REFERENCES users(u_id)
	);
	
CREATE TABLE jjim(
	j_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	j_c_id INT NOT NULL,
	j_u_id VARCHAR(50) NOT NULL,
	j_i_id INT NOT NULL,
	j_state BOOLEAN NOT NULL,
	j_date DATE NOT NULL,
	FOREIGN KEY(j_c_id) REFERENCES contents(c_id),
	FOREIGN KEY(j_u_id) REFERENCES users(u_id),
	FOREIGN KEY(j_i_id) REFERENCES interworking(i_id)
	);
	
CREATE TABLE watching_log(
	w_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	w_c_id INT NOT NULL,
	w_u_id VARCHAR(50) NOT NULL,
	w_i_id INT NOT NULL,
	w_time TIME NOT NULL,
	FOREIGN KEY(w_c_id) REFERENCES contents(c_id),
	FOREIGN KEY(w_u_id) REFERENCES users(u_id),
	FOREIGN KEY(w_i_id) REFERENCES interworking(i_id)
	);
	

INSERT INTO users(u_id, u_passwd, u_name, u_nickname, u_gender, u_age, u_subs, u_information)
VALUES('lantern50@kpu.ac.kr', 'jinho123', '김진호', 'jinhoho', true, 25, '넷플릭스', '첫번째로 가입한 유저'),
('wendy111@naver.com','hj228', '윤혜진', 'hyejjin', false, 24, '왓챠챠','두번째 유저'),
('eoeo0326@gmail.com', 'eoeo0326', '김재현', 'jaehyun', true, 25, '넷플릭스', '김김재재현현'),
('chpark@naver.com', 'chch97', '박찬호', 'chanho', true, 26, '티빙', '찬호형'),
('living@gmail.com', 'lv100', '리빙티슈', 'livingzz', false, 20, '티빙/왓챠', '3번째 유절');

INSERT INTO contents(c_id, c_genre, c_name, c_release_date, c_cast, c_platform, c_information, c_preview_url, c_number, c_age_limit, c_rating)
VALUES(1, '드라마', '마이네임', '2021-10-15','한소희/안보현/박희순/김상호/이학주/장률/문상민', '넷플릭스', 
'아빠를 잃었다. 그것도 바로 눈앞에서. 남은 딸은 결심한다. 반드시 내손으로 복수하겠노라고, 목표를 위해서라면 방법은 상관없다. 마약 조직의 언더커버가 되어 경찰에 잠입하는 것이라해도.',
'https://www.youtube.com/watch?v=q7i7XY3VE20', 0, '청소년 관람 불가', 9.0);

INSERT INTO contents_review(cr_id, cr_c_id, cr_u_id, cr_like, cr_rating, cr_review)
VALUES(1, 1, 'lantern50@kpu.ac.kr', TRUE, 10.0,'음 넷플릭스 드라마 역시 재밌군.'); 

INSERT INTO ranking(r_id, r_c_id, r_change)
VALUES(1, 1, -3);

INSERT INTO interworking(i_id, i_u_id, i_c_id, i_ott_name, i_ott_id, i_ott_passwd, i_ott_profile)
VALUES(1, 'lantern50@kpu.ac.kr', 1, '넷플릭스', 'lantern50', 'jinho118', '그라가스');

INSERT INTO watching_log(w_id, w_c_id, w_u_id, w_i_id, w_time)
VALUES(1, 1, 'lantern50@kpu.ac.kr', 1, '01:10:08');

INSERT INTO jjim(j_id, j_c_id, j_u_id, j_i_id, j_state, j_date)
VALUES(1, 1, 'lantern50@kpu.ac.kr', 1, FALSE, '2022-01-02');