ALTER TABLE board
	DROP
		CONSTRAINT FK_users_TO_board
		CASCADE;

ALTER TABLE board
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

DROP INDEX PK_board;

/* 게시판 */
DROP TABLE board 
	CASCADE CONSTRAINTS;

/* 게시판 */
CREATE TABLE board (
	no NUMBER(10) NOT NULL, /* 번호 */
	title varchar2(200), /* 글제목 */
	contents varchar2(4000), /* 글내용 */
	view_count NUMBER(10), /* 조회수 */
	reg_date DATE, /* 등록일 */
	users__no NUMBER(10) /* 사용자번호 */
);

COMMENT ON TABLE board IS '게시판';

COMMENT ON COLUMN board.no IS '번호';

COMMENT ON COLUMN board.title IS '글제목';

COMMENT ON COLUMN board.contents IS '글내용';

COMMENT ON COLUMN board.view_count IS '조회수';

COMMENT ON COLUMN board.reg_date IS '등록일';

COMMENT ON COLUMN board.users__no IS '사용자번호';

CREATE UNIQUE INDEX PK_board
	ON board (
		no ASC
	);

ALTER TABLE board
	ADD
		CONSTRAINT PK_board
		PRIMARY KEY (
			no
		);

ALTER TABLE board
	ADD
		CONSTRAINT FK_users_TO_board
		FOREIGN KEY (
			users__no
		)
		REFERENCES users (
			no
		);