--테이블 및 시퀀스 생성
drop table coupon;
drop table profession;
drop table Myani;
drop table favorite;
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
  id varchar2(40) not null,
  bcdate timestamp DEFAULT systimestamp not null,
  budate timestamp DEFAULT systimestamp,
  bhit number(5) DEFAULT 0 not null,
  bgood number(5) DEFAULT 0 not null,
  breply number(5) DEFAULT 0 not null,
  bcontent clob not null,
  bgroup number(5),
  constraint BOARD_BNUM_PK primary key(bnum)
  ,constraint board_id_FK foreign key(id) 
                                 references member(id) 
);

--댓글
create table rboard(
  rnum number(10),
  bnum number(10) not null,
  id varchar2(40) not null,
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
  ,constraint rboard_id_FK foreign key(id) 
                                 references member(id)
);

--건강정보
create table hboard(
  bnum number(10),
  hcategory varchar2(12) not null check(hcategory='질병사전' 
                              or hcategory='행동사전'),
  btitle varchar2(150) not null,
  id varchar2(40) not null,
  bcdate timestamp DEFAULT systimestamp not null,
  budate timestamp DEFAULT systimestamp,
  bhit number(5) DEFAULT 0 not null,
  bgood number(5) DEFAULT 0 not null,
  bcontent clob not null,
  constraint HBOARD_BNUM_PK primary key(bnum)
  ,constraint hboard_id_FK foreign key(id) 
                                 references member(id)
);

--좋아요게시글
create table goodboard(
  gnum number(10),
  id varchar2(40) not null,
  bnum number(10) not null,
  constraint GOODBOARD_gnum_PK primary key(gnum)
  ,constraint goodboard_id_FK foreign key(id) 
                                 references member(id)
                                 ON DELETE CASCADE
  ,constraint goodboard_bnum_FK foreign key(bnum) 
                                 references board(bnum)
                                 ON DELETE CASCADE
);

--업체
create table business(
  bnum number(10),
  bbnum varchar2(20),
  id varchar2(40) not null,
  bname varchar2(30) not null,
  baddress varchar2 (150) not null,
  btel varchar2(13) not null,
  nightcare char(1) check(nightcare = 'Y' or nightcare ='N'),
  rareani char(1) check(rareani = 'Y' or rareani ='N'),
  visitcare char(1) check(visitcare = 'Y' or visitcare ='N'),
  holidayopen char(1) check(holidayopen = 'Y' or holidayopen ='N'),
  dental char(1) check(dental = 'Y' or dental ='N'),
  openhours clob,
  constraint BUSINESS_BNUM_PK primary key(bnum)
  ,constraint business_id_FK foreign key(id) 
                                 references member(id)
                                 ON DELETE CASCADE
);

--전문가
create table profession(
  pnum number(10),
  id varchar2(40),
  licenseno varchar2(20) not null,

  constraint PROFESSION_PNUM_PK primary key(pnum)
  ,constraint profession_pid_FK foreign key(id) 
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
  id varchar2(40) not null,
  rvcdate timestamp DEFAULT systimestamp not null,
  rvudate timestamp DEFAULT systimestamp,
  constraint REVIEW_RNUM_PK primary key(rnum)
  ,constraint review_bnum_FK foreign key(bnum) 
                                 references business(bnum)
                                 ON DELETE CASCADE
  ,constraint review_id_FK foreign key(id) 
                                 references member(id)
);

--즐겨찾기
create table favorite(
  id varchar2(40),
  bnum number(10),
  mnum number(10),
  constraint MYLIST_mnum_PK primary key(mnum)
  ,constraint mylist_id_FK foreign key(id) 
                                 references member(id)
                                 ON DELETE CASCADE
  ,constraint mylist_bnum_FK foreign key(bnum)
                                 references business(bnum)
                                 ON DELETE CASCADE
);

--키우는 동물
create table myani(
  ID varchar2(40),
  ANIMAL varchar(30),
  MNUM number(10),
  constraint myani_MNUM_PK primary key(MNUM)
  ,constraint myani_id_FK foreign key(ID) 
                                 references member(id)
                                 ON DELETE CASCADE
);

--쿠폰
create table coupon(
  cnum number(10),
  id varchar2(40),
  price number(5),
  cflag char(1) DEFAULT 'Y' check(cflag = 'Y' or cflag ='N')
  ,constraint coupon_PK primary key(cnum)
  ,constraint coupon_FK foreign key(id)
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
CREATE SEQUENCE BOARD_BNUM_SEQ;
CREATE SEQUENCE rboard_rnum_seq;
CREATE SEQUENCE goodboard_gnum_seq;
CREATE SEQUENCE hboard_bnum_seq;
CREATE SEQUENCE business_bnum_seq;
CREATE SEQUENCE review_rnum_seq;
CREATE SEQUENCE mylist_mnum_seq;
CREATE SEQUENCE myani_mnum_seq;
CREATE SEQUENCE profession_pnum_seq;
CREATE SEQUENCE coupon_cnum_seq;

insert into member(ID,PW,TEL,EMAIL,NAME,NICKNAME,GENDER,ADDRESS,BIRTH,MTYPE) values('normal@zxc.com','zxc12345','000-0000-0000','zxc@zxc.com','일반인','휴먼','M','힘내면 잘되리','21/01/01','N');

insert into member(ID,PW,TEL,EMAIL,NAME,NICKNAME,GENDER,ADDRESS,BIRTH,MTYPE) values('special@zxc.com','zxc12345','111-1111-1111','cxz@cxz.com','특별한','여신','F','잘하구 재밌동','21/01/01','S');

insert into BUSINESS(BNUM,BBNUM,ID,BNAME,BADDRESS,BTEL,NIGHTCARE,RAREANI,VISITCARE,HOLIDAYOPEN,DENTAL)
values(BUSINESS_BNUM_SEQ.nextval,'사업자번호','special@zxc.com','물어!','코드도 깨끄시','333-3333-3333','Y','Y','Y','Y','Y');

insert into review(RNUM,BNUM,RSCORE,RCONTENT,ID) values(REVIEW_RNUM_SEQ.nextval,1,0,'좋아좋아','normal@zxc.com');

insert into board(BNUM,BCATEGORY,BTITLE,ID,BCONTENT) values(BOARD_BNUM_SEQ.nextval,'Q','지,질문드리겠습니다','normal@zxc.com','필요없어');
insert into board(BNUM,BCATEGORY,BTITLE,ID,BCONTENT) values(BOARD_BNUM_SEQ.nextval,'M','ㅍㅍ','normal@zxc.com','제시');
insert into board(BNUM,BCATEGORY,BTITLE,ID,BCONTENT) values(BOARD_BNUM_SEQ.nextval,'F','애니모어 힘내요','normal@zxc.com','ㅈㄱㄴ');
insert into board(BNUM,BCATEGORY,BTITLE,ID,BCONTENT) values(BOARD_BNUM_SEQ.nextval,'P','세계관 최강 귀요미들','normal@zxc.com','이거 보여주려고 어그로 끌었다');

insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP) values(rboard_RNUM_seq.nextval,1,'normal@zxc.com','뭔데',1,1);
insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP) values(rboard_RNUM_seq.nextval,2,'normal@zxc.com','님선',1,1);
insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP) values(rboard_RNUM_seq.nextval,3,'normal@zxc.com','ㅍㅇㅌ',1,1);
insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP) values(rboard_RNUM_seq.nextval,4,'normal@zxc.com','가슴이 웅장해진다',1,1);

commit;