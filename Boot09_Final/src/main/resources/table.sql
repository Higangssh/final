CREATE TABLE user_tbl(
	id NUMBER PRIMARY KEY,
	userName VARCHAR2(20) UNIQUE,
	password VARCHAR2(100) NOT NULL,
	email VARCHAR2(100) UNIQUE,
	role VARCHAR2(10) NOT NULL
);

DROP TABLE user_tbl;

CREATE SEQUENCE user_seq;

CREATE TABLE board_gallery(
	num NUMBER PRIMARY KEY,
	writer VARCHAR2(100),
	caption VARCHAR2(100),
	saveFileName VARCHAR2(100),
	regdate DATE
);

CREATE SEQUENCE board_gallery_seq;


