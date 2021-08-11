//계정생성
DROP USER c##animore CASCADE;
CREATE USER c##animore IDENTIFIED BY animore1234 DEFAULT TABLESPACE users TEMPORARY
TABLESPACE temp PROFILE DEFAULT;
GRANT CONNECT, RESOURCE TO c##animore;
GRANT CREATE VIEW, CREATE SYNONYM TO c##animore;
GRANT UNLIMITED TABLESPACE TO c##animore;
ALTER USER c##animore ACCOUNT UNLOCK;

//테이블 및 시퀀스 생성
drop table coupon;
drop table profession;
drop table Myani;
drop table MYLIST;
drop table REVIEW;
drop table BCATEGORY;
drop table BUSINESS;
drop table GOODBOARD;
drop table HBOARD;
drop table RBOARD;
drop table BOARD;
drop table member;

--회원
create table member(
  id varchar2(40),
  pw varchar2 (16) not null,
  tel varchar2(13) not null,
  email varchar2(30) not null,
  name varchar2(30) not null,
  nickname varchar2(30) not null,
  gender char(3) not null,
  address varchar2(150) not null,
  birth date not null,
  mtype varchar2(1) not null check(mtype='N' or mtype='S'),
  cdate timestamp DEFAULT systimestamp not null,
  udate timestamp DEFAULT systimestamp,
  image blob,
  fsize varchar2(45),
  ftype varchar2(50),
  fname varchar2(150),
  mileage number(6) DEFAULT 0 not null ,
  constraint MEMBER_ID_PK primary key(id)
);

--게시글
create table board(
  bnum number(10),
  bcategory char(1) not null check
                        (bcategory='Q'or bcategory='F'
                        or bcategory='M' or bcategory='P'),
  btitle varchar2(150) not null,
  bid varchar2(40) not null,
  bcdate timestamp DEFAULT systimestamp not null,
  budate timestamp DEFAULT systimestamp,
  bhit number(5) DEFAULT 0 not null,
  bgood number(5) DEFAULT 0 not null,
  breply number(5) DEFAULT 0 not null,
  bcontent clob not null,
  bgroup number(5),
  constraint BOARD_BNUM_PK primary key(bnum)
  ,constraint board_id_FK foreign key(bid) 
                                 references member(id) 
);

--댓글
create table rboard(
  rnum number(10),
  bnum number(10) not null,
  rid varchar2(40) not null,
  rcdate timestamp DEFAULT systimestamp not null,
  rudate timestamp DEFAULT systimestamp,
  rcontent varchar2(600) not null,
  rgood number(5) DEFAULT 0 not null,
  rgroup number(5) not null,
  rstep number(5) not null,
  constraint RBOARD_RNUM_PK primary key(rnum)
  ,constraint rboard_bnum_FK foreign key(bnum) 
                                 references board(bnum)
                                 ON DELETE CASCADE
  ,constraint rboard_id_FK foreign key(rid) 
                                 references member(id)
);

--건강정보
create table hboard(
  bnum number(10),
  hcategory varchar2(12) not null check(hcategory='질병사전' 
                              or hcategory='행동사전'),
  btitle varchar2(150) not null,
  bid varchar2(40) not null,
  bcdate timestamp DEFAULT systimestamp not null,
  budate timestamp DEFAULT systimestamp,
  bhit number(5) DEFAULT 0 not null,
  bgood number(5) DEFAULT 0 not null,
  bcontent clob not null,
  constraint HBOARD_BNUM_PK primary key(bnum)
  ,constraint hboard_id_FK foreign key(bid) 
                                 references member(id)
);

--좋아요게시글
create table goodboard(
  gnum number(10),
  gid varchar2(40) not null,
  gbnum number(10) not null,
  constraint GOODBOARD_gnum_PK primary key(gnum)
  ,constraint goodboard_id_FK foreign key(gid) 
                                 references member(id)
                                 ON DELETE CASCADE
  ,constraint goodboard_bnum_FK foreign key(gbnum) 
                                 references board(bnum)
                                 ON DELETE CASCADE
);

--업체
create table business(
  bnum number(10),
  bbnum varchar2(20),
  bid varchar2(40) not null,
  bname varchar2(30) not null,
  baddress varchar2 (150) not null,
  btell varchar2(13) not null,
  nightcare char(1) check(nightcare = 'Y' or nightcare ='N'),
  rareani char(1) check(rareani = 'Y' or rareani ='N'),
  visitcare char(1) check(visitcare = 'Y' or visitcare ='N'),
  holidayopen char(1) check(holidayopen = 'Y' or holidayopen ='N'),
  dental char(1) check(dental = 'Y' or dental ='N'),
  openhours clob
  constraint BUSINESS_BNUM_PK primary key(bnum)
  ,constraint business_id_FK foreign key(bid) 
                                 references member(id)
                                 ON DELETE CASCADE
);

--전문가
create table profession(
  pnum number(10),
  pid varchar2(40),
  licenseno varchar2(20) not null,

  constraint PROFESSION_PNUM_PK primary key(pnum)
  ,constraint profession_pid_FK foreign key(pid) 
                                 references member(id)
                                 ON DELETE CASCADE
);

--업체카테고리
create table bcategory(
  bnum number(10),
  bhospital char(1) DEFAULT 'N' not null check(bhospital = 'Y' or bhospital ='N'),
  bpharmacy char(1) DEFAULT 'N' not null check(bpharmacy = 'Y' or bpharmacy ='N'),
  bhotel char(1) DEFAULT 'N' not null check(bhotel = 'Y' or bhotel ='N'),
  bkindergarden char(1) DEFAULT 'N' not null check(bkindergarden = 'Y' or bkindergarden ='N'),
  bfood char(1) DEFAULT 'N' not null check(bfood = 'Y' or bfood ='N'),
  btraining char(1) DEFAULT 'N' not null check(btraining = 'Y' or btraining ='N'),
  bshop char(1) DEFAULT 'N' not null check(bshop = 'Y' or bshop ='N'),
  bplayground char(1) DEFAULT 'N' not null check(bplayground = 'Y' or bplayground ='N'),
  bhairshop char(1) DEFAULT 'N' not null check(bhairshop = 'Y' or bhairshop ='N'),
  betc char(1) DEFAULT 'N' not null check(betc = 'Y' or betc ='N'),
  constraint BCATEGORY_BNUM_PK primary key(bnum)
  ,constraint bcategory_bnum_FK foreign key(bnum) 
                                 references business(bnum)
                                 ON DELETE CASCADE
);

--후기
create table review(
  rnum number(10),
  bnum number(10) not null,
  rscore number(1,1) not null,
  rcontent clob not null,
  rvid varchar2(40) not null,
  rvcdate timestamp DEFAULT systimestamp not null,
  rvudate timestamp DEFAULT systimestamp,
  constraint REVIEW_RNUM_PK primary key(rnum)
  ,constraint review_bnum_FK foreign key(bnum) 
                                 references business(bnum)
                                 ON DELETE CASCADE
  ,constraint review_id_FK foreign key(rvid) 
                                 references member(id)
);

--즐겨찾기
create table mylist(
  mid varchar2(40),
  mbnum number(10),
  mnum number(10),
  constraint MYLIST_mnum_PK primary key(mnum)
  ,constraint mylist_id_FK foreign key(mid) 
                                 references member(id)
                                 ON DELETE CASCADE
  ,constraint mylist_bnum_FK foreign key(mbnum)
                                 references business(bnum)
                                 ON DELETE CASCADE
);

--키우는 동물
create table myani(
  MID varchar2(40),
  ANIMAL varchar(30),
  MNUM number(10),
  constraint myani_MNUM_PK primary key(MNUM)
  ,constraint myani_id_FK foreign key(MID) 
                                 references member(id)
                                 ON DELETE CASCADE
);

--쿠폰
create table coupon(
  cnum number(10),
  cid varchar2(40),
  price number(5),
  cflag char(1) DEFAULT 'Y' check(cflag = 'Y' or cflag ='N')
  ,constraint coupon_PK primary key(cnum)
  ,constraint coupon_FK foreign key(cid)
                              references member(id)
                              ON DELETE CASCADE
);

--시퀀스 삭제
DROP SEQUENCE BOARD_BNUM_SEQ;
DROP SEQUENCE rboard_rnum_seq;
DROP SEQUENCE goodboard_gnum_seq;
DROP SEQUENCE hboard_bnum_seq;
DROP SEQUENCE business_bnum_seq;
DROP SEQUENCE review_rnum_seq;
DROP SEQUENCE mylist_mnum_seq;
DROP SEQUENCE myani_mnum_seq;
DROP SEQUENCE profession_pnum_seq;
DROP SEQUENCE coupon_cnum_seq;

--시퀀스 생성
CREATE SEQUENCE BOARD_BNUM_SEQ INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE rboard_rnum_seq INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE goodboard_gnum_seq INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE hboard_bnum_seq INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE business_bnum_seq INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE review_rnum_seq INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE mylist_mnum_seq INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE myani_mnum_seq INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE profession_pnum_seq INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE coupon_cnum_seq INCREMENT BY 1 START WITH 1;





select * from member;
select * from business;
select * from bcategory;
select * from myani;
select * from profession;

rollback;


--일반회원 가입쿼리
insert all
into MEMBER(ID,PW,TEL,EMAIL,NAME,NICKNAME,GENDER,ADDRESS,BIRTH,MTYPE)
  values ('test2@test.com','zxc123!@','010-1234-1234','petlove1@kh.com','홍길동','반려부자','남','울산','20001010','N')
into myani(MNUM,MID,ANIMAL) values(myani_mnum_seq.nextval,'test2@test.com','고양이')
  select * from dual;

--사업자회원 가입쿼리
insert all
into MEMBER(ID,PW,TEL,EMAIL,NAME,NICKNAME,GENDER,ADDRESS,BIRTH,MTYPE)
  values ('test2@test.com','zxc123!@','010-1234-1234','petlove1@kh.com','홍길동','반려부자','남','울산','20001010','B')
into BUSINESS(BNUM, BBNUM, BID, BNAME, BADDRESS, BTELL)
  values(business_bnum_seq.nextval,112,'test2@test.com','업체명1','업체주소1','010-1111-2222')
into BCATEGORY(BNUM, BPHARMACY) values(business_bnum_seq.currval,'Y')
select * from dual;



update MEMBER
set PW='12341',TEL='010-1111-1111',EMAIL='test2@googo.com',NAME='홍수정',NICKNAME='수정별명1',GENDER='여',ADDRESS='울산수정',BIRTH='20001231'
where id='test2@test.com';



delete from member
where id='test2@test.com';

select * from member;

select id,nickname,mtype from member
		where id='test3@test.com'
    and pw='1234';