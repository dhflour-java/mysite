drop table board;
drop sequence board_no_seq;

CREATE TABLE board
( 
no           NUMBER(8),
title        VARCHAR2(200) NOT NULL,
content      VARCHAR2(4000) NOT NULL,
member_no    NUMBER(8),
member_name  VARCHAR2(30),
view_cnt     NUMBER(10),
reg_date     DATE NOT NULL
) ;

ALTER TABLE board
ADD ( CONSTRAINT board_no_pk PRIMARY KEY ( no ) );


CREATE SEQUENCE board_no_seq
 START WITH     1
 INCREMENT BY   1
 MAXVALUE       99999999
 NOCACHE
 NOCYCLE;
 