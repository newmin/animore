--테이블 및 시퀀스 생성
drop table reviewfile;
drop table businessfile;
drop table boardfile;
drop table favorite;
drop table REVIEW;
drop table BCATEGORY;
drop table BUSINESS;
drop table GOODBOARD;
drop table RBOARD;
drop table BOARD;
drop table myani;
drop table coupon;
drop table member;

--회원
create table member(
  id varchar2(40),
  pw varchar2 (16) not null,
  tel varchar2(4) not null,
  tel2 varchar2(4) not null,
  tel3 varchar2(4) not null,
  email varchar2(30) not null,
  name varchar2(15) not null,
  nickname varchar2(30) not null,
  gender char(3) not null,
  address varchar2(150) not null,
  birth date not null,
  mtype char(1) not null,
  myani varchar2(30),
  status char(1) DEFAULT 'A' not null, --회원상태  활성:Active, 휴면:Dormancy, 탈퇴:Withdraw, 정지:Suspended
  cdate timestamp DEFAULT systimestamp not null,
  udate timestamp,
  lastlogin timestamp DEFAULT systimestamp, --마지막 로그인 시각
  fsize varchar2(45),
  ftype varchar2(50),
  store_fname varchar2(150),
  upload_fname varchar2(150),
  mileage number(6) DEFAULT 0 not null,
  constraint MEMBER_ID_PK primary key(id),
  constraint MEMBER_STATUS_CK check(status in ('A','D','S','W')),
  constraint MEMBER_mtype_ck check(mtype in('A','N','S')),
  constraint MEMBER_ftype_ck check(ftype like 'image/%')  
);
insert into member(ID,PW,TEL,TEL2,TEL3,EMAIL,NAME,NICKNAME,GENDER,ADDRESS,BIRTH,MTYPE) values('admin@animore.com','zxc12345','000','0000','0000','zxc@zxc.com','관리자','관리자','M','힘내면 잘되리','21/01/01','A');

--키우는 동물
create table myani(
  MNUM number(10),
  ID varchar2(40),
  ANIMAL varchar2(30),
  constraint myani_MNUM_PK primary key(MNUM),
  constraint myani_id_FK foreign key(ID) 
                                 references member(id)
                                 ON DELETE CASCADE
);

--게시글
create table board(
  bnum number(8),
  bcategory char(1) not null,
  btitle varchar2(150) not null,
  id varchar2(40) not null,
  bcdate timestamp DEFAULT systimestamp not null,
  budate timestamp DEFAULT systimestamp,
  bhit number(5) DEFAULT 0 not null,
  bgood number(5) DEFAULT 0 not null,
  breply number(5) DEFAULT 0 not null,
  bcontent clob not null,
  pbnum number(10),               --부모게시글번호
  bgroup number(5),
  bstep number(3),                
  bindent number(3),
  bstatus char(1) default 'N',    --공지여부
  bndate timestamp ,              --공지업로드시간
  constraint BOARD_BNUM_PK primary key(bnum),
  constraint board_bcategory_ck check (bcategory in('Q','F','M','P'))
);
--게시판 이미지업로드
create table boardfile(
fnum number(10),
bnum number (8),
store_fname varchar2(50),
upload_fname varchar2(50),
fsize varchar2(45),
ftype varchar2(50),
cdate timestamp default systimestamp,
udate timestamp,
constraint boardfile_fnum_pk primary key(fnum),
constraint boardfile_bnum_fk foreign key(bnum)references board(bnum) on delete cascade
);
--댓글
create table rboard(
  rnum number(10),
  bnum number(8) not null,
  id varchar2(40) not null,
  rcdate timestamp DEFAULT systimestamp not null,
  rudate timestamp,
  rcontent varchar2(600) not null,
  rgood number(5) DEFAULT 0 not null,
  rgroup number(5) default 0 not null,
  rstep number(5) default 0 not null,
  rindent number(5) default 0 not null,
  prnum number(10),
  status char (1) default 'A' not null, --활성(A), 삭제(D)
  constraint RBOARD_RNUM_PK primary key(rnum),
  constraint rboard_bnum_FK foreign key(bnum) 
                                references board(bnum)
                                ON DELETE CASCADE
);



--좋아요게시글
create table goodboard(
  gnum number(8),
  id varchar2(40) not null,
  bnum number(8) not null,
  constraint GOODBOARD_gnum_PK primary key(gnum),
  constraint goodboard_id_FK foreign key(id) 
                                references member(id)
                                ON DELETE CASCADE,
  constraint goodboard_bnum_FK foreign key(bnum) 
                                references board(bnum)
                                ON DELETE CASCADE
);

--업체
create table business(
  bnum number(8),
  bbnum varchar2(20),
  id varchar2(40) default 'admin@animore.com',
  bname varchar2(150) not null,
  baddress varchar2 (150) not null,
  btel varchar2(14),
--  btel2 NUMBER(4) not null,
--  btel3 NUMBER(4) not null,
  openhours clob,
  nightcare char(1),
  rareani char(1),
  visitcare char(1),
  holidayopen char(1),
  dental char(1),
  constraint BUSINESS_BNUM_PK primary key(bnum),
  constraint business_id_FK foreign key(id) 
                                references member(id)
                                ON DELETE CASCADE,
  constraint business_nightcare_ck check(nightcare in('Y','N')),
  constraint business_rareani_ck check(rareani in('Y','N')),
  constraint business_visitcare_ck check(visitcare in('Y','N')),
  constraint business_holidayopen_ck check(holidayopen in('Y','N')),
  constraint business_dental_ck check(dental in('Y','N'))
);
--업체 이미지업로드
create table businessfile(
fnum number(10),
bnum number (8),
store_fname varchar2(50),
upload_fname varchar2(50),
fsize varchar2(45),
ftype varchar2(50),
cdate timestamp default systimestamp,
udate timestamp,
constraint businessfile_fnum_pk primary key(fnum),
constraint businessfile_bnum_fk foreign key(bnum)references business(bnum) on delete cascade
);


--업체카테고리
create table bcategory(
  bnum number(8),
  bhospital char(1) DEFAULT 'N'  null,
  bpharmacy char(1) DEFAULT 'N'  null,
  bhotel char(1) DEFAULT 'N'  null,
  bkindergarden char(1) DEFAULT 'N'  null,
  bfood char(1) DEFAULT 'N'  null,
  btraining char(1) DEFAULT 'N'  null,
  bshop char(1) DEFAULT 'N'  null,
  bplayground char(1) DEFAULT 'N'  null,
  bhairshop char(1) DEFAULT 'N'  null,
  betc char(1) DEFAULT 'N'  null,
  constraint BCATEGORY_BNUM_PK primary key(bnum),
  constraint bcategory_bnum_FK foreign key(bnum) 
                                references business(bnum)
                                ON DELETE CASCADE,
  constraint dental_bhospital_ck check(bhospital in('Y','N')),
  constraint dental_bpharmacy_ck check(bpharmacy in('Y','N')),
  constraint dental_bhotel_ck check(bhotel in('Y','N')),
  constraint dental_bkindergarden_ck check(bkindergarden in('Y','N')),
  constraint dental_bfood_ck check(bfood in('Y','N')),
  constraint dental_btraining_ck check(btraining in('Y','N')),
  constraint dental_bshop_ck check(bshop in('Y','N')),
  constraint dental_bplayground_ck check(bplayground in('Y','N')),
  constraint dental_bhairshop_ck check(bhairshop in('Y','N')),
  constraint dental_betc_ck check(betc in('Y','N'))
);

--리뷰
create table review(
  rnum number(10),
  bnum number(8) not null,
  rscore number(1) not null,
  rcontent clob not null,
  id varchar2(40) not null,
  rvReply clob,
  rvcdate timestamp DEFAULT systimestamp not null,
  rvudate timestamp,
  constraint REVIEW_RNUM_PK primary key(rnum),
  constraint review_bnum_FK foreign key(bnum) 
                                references business(bnum)
                                ON DELETE CASCADE
);
--리뷰 이미지업로드
create table reviewfile(
fnum number(10),
rnum number (10),
store_fname varchar2(50),
upload_fname varchar2(50),
fsize varchar2(45),
ftype varchar2(50),
cdate timestamp default systimestamp,
udate timestamp,
constraint reviewfile_fnum_pk primary key(fnum),
constraint reviewfile_rnum_fk foreign key(rnum)references review(rnum) on delete cascade
);

--즐겨찾기
create table favorite(
  fnum number(10),
  bnum number(8),
  id varchar2(40),
  fdate timestamp default systimestamp,
  constraint favorite_fnum_PK primary key(fnum),
  constraint favorite_id_FK foreign key(id) 
                                 references member(id)
                                 ON DELETE CASCADE,
  constraint favorite_bnum_FK foreign key(bnum)
                                 references business(bnum)
                                 ON DELETE CASCADE
);

--내쿠폰
create table coupon(
  cnum number(10),
  id varchar2(40),
  price number(5),
  cflag char(1) default 'Y',
  constraint coupon_cnum_PK primary key(cnum),
  constraint coupon_id_FK foreign key(id)
                          references member(id)
                          ON DELETE CASCADE
);

--시퀀스 삭제
DROP SEQUENCE BOARD_BNUM_SEQ;
DROP SEQUENCE rboard_rnum_seq;
DROP SEQUENCE goodboard_gnum_seq;
DROP SEQUENCE business_bnum_seq;
DROP SEQUENCE review_rnum_seq;
DROP SEQUENCE reviewfile_fnum_seq;
DROP SEQUENCE favorite_fnum_seq;
DROP SEQUENCE myani_mnum_seq;
drop sequence boardfile_fnum_seq;
drop sequence businessfile_fnum_seq;
drop sequence review_fnum_seq;
drop sequence coupon_cnum_seq;

--시퀀스 생성
CREATE SEQUENCE BOARD_BNUM_SEQ;
CREATE SEQUENCE rboard_rnum_seq;
CREATE SEQUENCE goodboard_gnum_seq;
CREATE SEQUENCE business_bnum_seq;
CREATE SEQUENCE review_rnum_seq;
CREATE SEQUENCE reviewfile_fnum_seq;
CREATE SEQUENCE favorite_fnum_seq;
CREATE SEQUENCE myani_mnum_seq;
create sequence boardfile_fnum_seq;
create sequence businessfile_fnum_seq;
create sequence review_fnum_seq;
create sequence coupon_cnum_seq;

-- 임시데이터 등록(각 데이터별 2개 이상)
-- 일반회원
insert into member(ID,PW,TEL,TEL2,TEL3,EMAIL,NAME,NICKNAME,GENDER,ADDRESS,BIRTH,MTYPE,upload_fname,store_fname,ftype,fsize) values('normal@zxc.com','zxc12345','000','0000','0000','zxc@zxc.com','일반인','휴먼','M','힘내면 잘되리','21/01/01','N','logo.png','puppy2.png','image/png','16345');
insert into member(ID,PW,TEL,TEL2,TEL3,EMAIL,NAME,NICKNAME,GENDER,ADDRESS,BIRTH,MTYPE,upload_fname,store_fname,ftype,fsize) values('user@test.com','zxc12345','222','2222','2222','user@zxc.com','이사람','저사람','F','겨울이가면 돌아오리','20/01/01','N','logo.png','puppy2.png','image/png','16345');
-- 특수회원
insert into member(ID,PW,TEL,TEL2,TEL3,EMAIL,NAME,NICKNAME,GENDER,ADDRESS,BIRTH,MTYPE) values('special@zxc.com','zxc12345','111','1111','1111','cxz@cxz.com','특별한','여신','F','잘하구 재밌동','21/01/01','S');
insert into member(ID,PW,TEL,TEL2,TEL3,EMAIL,NAME,NICKNAME,GENDER,ADDRESS,BIRTH,MTYPE) values('busi@test.com','zxc12345','444','4444','4444','busi@cxz.com','굉장한','남신','M','지역구 금은동','20/01/01','S');
-- 업체
insert into BUSINESS(BNUM,BBNUM,ID,BNAME,BADDRESS,BTEL,NIGHTCARE,RAREANI,VISITCARE,HOLIDAYOPEN,DENTAL)
values(BUSINESS_BNUM_SEQ.nextval,'456-78-90123','special@zxc.com','동물사랑','울산광역시 남구 삼산로 74','333-3333-3333','Y','Y','Y','Y','Y');
insert into BCATEGORY values(BUSINESS_BNUM_SEQ.currval,'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y');

insert into BUSINESS(BNUM,BBNUM,ID,BNAME,BADDRESS,BTEL,NIGHTCARE,RAREANI,VISITCARE,HOLIDAYOPEN,DENTAL)
values(BUSINESS_BNUM_SEQ.nextval,'456-78-90124','special@zxc.com','우리강아지씻자','울산광역시 중구 병영로 4','333-3333-3333','Y','Y','Y','Y','Y');
insert into BCATEGORY values(BUSINESS_BNUM_SEQ.currval,'Y','Y','N','Y','Y','N','Y','Y','Y','N');

insert into BUSINESS(BNUM,BBNUM,ID,BNAME,BADDRESS,BTEL,NIGHTCARE,RAREANI,VISITCARE,HOLIDAYOPEN,DENTAL)
values(BUSINESS_BNUM_SEQ.nextval,'456-78-90125','special@zxc.com','맘마먹자','울산광역시 북구 화봉로 84','333-3333-3333','Y','Y','Y','Y','Y');
insert into BCATEGORY values(BUSINESS_BNUM_SEQ.currval,'Y','N','Y','Y','Y','Y','Y','Y','N','Y');

insert into BUSINESS(BNUM,BBNUM,ID,BNAME,BADDRESS,BTEL,NIGHTCARE,RAREANI,VISITCARE,HOLIDAYOPEN,DENTAL)
values(BUSINESS_BNUM_SEQ.nextval,'123-45-67890','busi@test.com','너랑나랑','울산광역시 남구 삼산동 1646','555-5555-5555','Y','Y','Y','Y','Y');
insert into BCATEGORY values(BUSINESS_BNUM_SEQ.currval,'Y','Y','N','Y','N','Y','Y','Y','Y','Y');

insert into BUSINESS(BNUM,BBNUM,ID,BNAME,BADDRESS,BTEL,NIGHTCARE,RAREANI,VISITCARE,HOLIDAYOPEN,DENTAL)
values(BUSINESS_BNUM_SEQ.nextval,'123-45-67823','busi@test.com','with','경상남도 양산시 양산역6길 12 신세계이마트양산점','555-5555-5555','Y','Y','Y','Y','Y');
insert into BCATEGORY values(BUSINESS_BNUM_SEQ.currval,'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y');

-- 업체별 리뷰
insert into review(RNUM,BNUM,RSCORE,RCONTENT,ID) values(REVIEW_RNUM_SEQ.nextval,1,3,'좋아좋아','normal@zxc.com');
insert into review(RNUM,BNUM,RSCORE,RCONTENT,ID) values(REVIEW_RNUM_SEQ.nextval,1,2,'별루별루','user@test.com');
insert into review(RNUM,BNUM,RSCORE,RCONTENT,ID) values(REVIEW_RNUM_SEQ.nextval,2,4,'좋아좋아','normal@zxc.com');
insert into review(RNUM,BNUM,RSCORE,RCONTENT,ID) values(REVIEW_RNUM_SEQ.nextval,2,5,'별루별루','user@test.com');
-- 게시글
insert into board(BNUM,BCATEGORY,BTITLE,ID,BCONTENT,bgroup,bstep,bindent) values(BOARD_BNUM_SEQ.nextval,'Q','지,질문드리겠습니다','normal@zxc.com','필요없어',BOARD_BNUM_SEQ.currval,0,0);
insert into board(BNUM,BCATEGORY,BTITLE,ID,BCONTENT,bgroup,bstep,bindent) values(BOARD_BNUM_SEQ.nextval,'M','ㅍㅍ','normal@zxc.com','제시요',BOARD_BNUM_SEQ.currval,0,0);
insert into board(BNUM,BCATEGORY,BTITLE,ID,BCONTENT,bgroup,bstep,bindent) values(BOARD_BNUM_SEQ.nextval,'F','애니모어 힘내요','normal@zxc.com','ㅈㄱㄴ',BOARD_BNUM_SEQ.currval,0,0);
insert into board(BNUM,BCATEGORY,BTITLE,ID,BCONTENT,bgroup,bstep,bindent) values(BOARD_BNUM_SEQ.nextval,'P','세계관 최강 귀요미들','normal@zxc.com','이거 보여주려고 어그로 끌었다',BOARD_BNUM_SEQ.currval,0,0);
insert into board(BNUM,BCATEGORY,BTITLE,ID,BCONTENT,bgroup,bstep,bindent) values(BOARD_BNUM_SEQ.nextval,'Q','요즘 애니모어에 벽이 느껴지지 않나요?','user@test.com','<완벽>이라는 이름의 벽이요',BOARD_BNUM_SEQ.currval,0,0);
insert into board(BNUM,BCATEGORY,BTITLE,ID,BCONTENT,bgroup,bstep,bindent) values(BOARD_BNUM_SEQ.nextval,'M','중고 팔아여','user@test.com','새거처럼 깨끗해여',BOARD_BNUM_SEQ.currval,0,0);
insert into board(BNUM,BCATEGORY,BTITLE,ID,BCONTENT,bgroup,bstep,bindent) values(BOARD_BNUM_SEQ.nextval,'F','요즘 털빠짐이 심하네요..','user@test.com','제 머리에서요ㅠㅠ',BOARD_BNUM_SEQ.currval,0,0);
insert into board(BNUM,BCATEGORY,BTITLE,ID,BCONTENT,bgroup,bstep,bindent) values(BOARD_BNUM_SEQ.nextval,'P','인형에 진심인 우리애들','user@test.com','이미 인형 그 자체',BOARD_BNUM_SEQ.currval,0,0);
-- 댓글
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,1,'normal@zxc.com','뭔데',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,1,'user@test.com','돈드리겠습니다',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,2,'normal@zxc.com','님선',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,2,'user@test.com','10불러봅니다',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,3,'normal@zxc.com','ㅍㅇㅌ',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,3,'user@test.com','힘내요~',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,4,'normal@zxc.com','가슴이 웅장해진다',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,4,'user@test.com','그 작던 쪼꼬미들 맞냐',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,5,'normal@zxc.com','엌ㅋㅋ',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,5,'user@test.com','언제나 감사합니다',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,6,'normal@zxc.com','-판매완료-',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,6,'user@test.com','아직 안팔렸습니다. 윗댓 누구냐ㅡㅡ',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,7,'normal@zxc.com','"모"자람이 없으시네요',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,7,'user@test.com','너어는....',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,8,'normal@zxc.com','너무 귀여워요ㅠㅠ',1,1,0);
--insert into rboard(RNUM,BNUM,ID,RCONTENT,RGROUP,RSTEP,rindent) values(rboard_RNUM_seq.nextval,8,'user@test.com','네가 더',1,1,0);

--즐겨찾기
insert into favorite(fnum, bnum, id) values(favorite_fnum_seq.nextval, 1, 'normal@zxc.com');
insert into favorite(fnum, bnum, id) values(favorite_fnum_seq.nextval, 1, 'user@test.com');
insert into favorite(fnum, bnum, id) values(favorite_fnum_seq.nextval, 1, 'special@zxc.com');
insert into favorite(fnum, bnum, id) values(favorite_fnum_seq.nextval, 1, 'busi@test.com');
insert into favorite(fnum, bnum, id) values(favorite_fnum_seq.nextval, 2, 'normal@zxc.com');
insert into favorite(fnum, bnum, id) values(favorite_fnum_seq.nextval, 2, 'user@test.com');
insert into favorite(fnum, bnum, id) values(favorite_fnum_seq.nextval, 2, 'special@zxc.com');
insert into favorite(fnum, bnum, id) values(favorite_fnum_seq.nextval, 2, 'busi@test.com');

commit;

