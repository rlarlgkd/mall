# mall

Created Simple shopping mall using Spring Boots + ORACLE SQL



DATABASE USED: ORACLE SQL


DATA SAMPLE:

drop table notice_bbs;
drop table sale;
drop table review;
drop table cart;
drop table product;
drop table member;

create table member (
mid varchar2(20),
mpw varchar2(200),
mname varchar2(20),
postcode varchar2(20),
address varchar2(100),
detailAddress varchar2(100),
extraAddress varchar2(100),
enabled number(1),
usertype VARCHAR2(100) ,
constraint pk_member primary key(mid)
);

create table product (
pnum NUMBER,
mid varchar2(20),
pname varchar2(100),
price NUMBER,
hit NUMBER default 0,
pcnt NUMBER,
pic blob,
constraint pk_product primary key(pnum),
constraint fk_memberid
    foreign key (mid)
    References member (mid)
);
drop sequence p_seq;
create sequence p_seq;

create table review (
reid NUMBER,
mid varchar2(20),
pnum NUMBER,
regroup NUMBER,
restep NUMBER default 0,
reindent NUMBER default 0,
review varchar2(400),
constraint fk_member
    foreign key (mid)
    References member (mid)
);
drop sequence re_seq;
create sequence re_seq;

create table cart (
mid varchar2(20),
pnum NUMBER,
ccnt NUMBER,
constraint fk_member2
    foreign key (mid)
    References member (mid)
);

create table sale (
mid varchar2(20),
pnum NUMBER,
constraint fk_smember
    foreign key (mid)
    References member (mid)
);

create table notice_bbs (
mid varchar2(20),
nonum NUMBER,
notitle varchar2(100),
nocontent varchar2(400),
nodate date,
constraint fk_nmember
    foreign key (mid)
    References member (mid)
);
drop SEQUENCE n_seq;
create SEQUENCE n_seq;


----- user1~4 : 판매자  user5~8 : 구매자 user9~10: 관리자
insert into member (mid, mname, enabled) values ('visitor','visitor',1);
insert into member (mid, mpw, mname, usertype,enabled) values ('user1','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','user1','ROLE_SELLER',1);
insert into member (mid, mpw, mname, usertype,enabled) values ('user2','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','user2','ROLE_SELLER',1);
insert into member (mid, mpw, mname, usertype,enabled) values ('user3','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','user3','ROLE_SELLER',1);
insert into member (mid, mpw, mname, usertype,enabled) values ('user4','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','user4','ROLE_SELLER',1);
insert into member (mid, mpw, mname, usertype,enabled) values ('user5','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','user5','ROLE_BUYER',1);
insert into member (mid, mpw, mname, usertype,enabled) values ('user6','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','user6','ROLE_BUYER',1);
insert into member (mid, mpw, mname, usertype,enabled) values ('user7','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','user7','ROLE_BUYER',1);
insert into member (mid, mpw, mname, usertype,enabled) values ('user8','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','user8','ROLE_BUYER',1);
insert into member (mid, mpw, mname, usertype,enabled) values ('user9','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','user9','ROLE_ADMIN',1);
insert into member (mid, mpw, mname, usertype,enabled) values ('user10','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','user10','ROLE_ADMIN',1);
insert into member (mid, mpw, mname, postcode, address, detailAddress, extraAddress, usertype,enabled) values 
('buyer','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','buyer','03733','서울 서대문구 독립문공원길 17','0000-0000','(현저동, 독립문극동아파트)','ROLE_BUYER',1);
insert into member (mid, mpw, mname, postcode, address, detailAddress, extraAddress, usertype,enabled) values 
('seller','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','seller','03623','서울 성동구 무악현대 1','0000-0000','(무악동, 무악아파트)','ROLE_SELLER',1);
insert into member (mid, mpw, mname, postcode, address, detailAddress, extraAddress, usertype,enabled) values 
('admin','$2a$10$6NIWP08ivRI83Aszkg30z.6yjPrjKm6XGzdHMp.q8Rtj14t67O8Te','admin','02283','서울 종로구 무악대치 777','0000-0000','(대치동, 대치아파트)','ROLE_ADMIN',1);





----------- 셀러만 상품 업로드 가능----------------------------------------------------
insert into product (pnum, mid, pname, price, hit, pcnt) values (p_seq.nextval, 'user1', '체크셔츠', 45000, 1 , 1000);
insert into product (pnum, mid, pname, price, hit, pcnt) values (p_seq.nextval,'user1', '구두', 23000, 0 , 100);
insert into product (pnum, mid, pname, price, hit, pcnt) values (p_seq.nextval,'user2', '나이키신발', 33000, 0 , 95);
insert into product (pnum, mid, pname, price, hit, pcnt) values (p_seq.nextval,'seller', '스웨터', 18000, 0 , 100);
insert into product (pnum, mid, pname, price, hit, pcnt) values (p_seq.nextval,'user3', '스웨터세트', 14500, 0 , 100);
insert into product (pnum, mid, pname, price, hit, pcnt) values (p_seq.nextval,'user3', '여성스웨터세트', 14000, 6 , 100);
insert into product (pnum, mid, pname, price, hit, pcnt) values (p_seq.nextval,'user4', '여성운동화', 43000, 4 , 100);
insert into product (pnum, mid, pname, price, hit, pcnt) values (p_seq.nextval,'user4', '원피스', 12000, 5 , 100);
insert into product (pnum, mid, pname, price, hit, pcnt) values (p_seq.nextval,'user3', '줄무늬신발', 1, 3 , 100);
insert into product (pnum, mid, pname, price, hit, pcnt) values (p_seq.nextval,'user3', '파랑스웨터', 52000, 2 , 100);
insert into product (pnum, mid, pname, price, hit, pcnt) values (p_seq.nextval,'user2', '티셔츠', 25000, 0, 1000);
insert into product (pnum, mid, pname, price, hit, pcnt) values (p_seq.nextval,'seller', '파란신발', 45000, 10, 1000);
commit;
--------------- 구매자만 리뷰 등록 가능-------------------------------------------------------------
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user5',11,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user5',7,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user5',2,'정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 
좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user5',1,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user6',2,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user6',3,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user6',4,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user6',5,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user7',5,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user7',6,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user7',7,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user7',8,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user8',9,'별로예요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user8',10,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'buyer',12,'신발이 편하고 튼튼하네요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user8',8,'정말 좋아요', re_seq.nextval);
insert into review (reid, mid, pnum, review, regroup) values (re_seq.nextval, 'user8',9,'정말 좋아요', re_seq.nextval);

---------- 구매자만 장바구니 사용 가능 ------------------
insert into cart (mid, pnum, ccnt) values ('user4',1,3);
insert into cart (mid, pnum, ccnt) values ('user5',2,1);
insert into cart (mid, pnum, ccnt) values ('buyer',11,2);
insert into cart (mid, pnum, ccnt) values ('buyer',7,3);
insert into cart (mid, pnum, ccnt) values ('user8',6,2);
insert into cart (mid, pnum, ccnt) values ('user8',5,4);
insert into cart (mid, pnum, ccnt) values ('user7',4,1);
insert into cart (mid, pnum, ccnt) values ('user6',3,5);
insert into cart (mid, pnum, ccnt) values ('user5',9,1);
insert into cart (mid, pnum, ccnt) values ('user5',1,1);

-------관리자만 공지사항 작성 가능 ----------------------------
insert into notice_bbs (mid, nonum, notitle, nocontent, nodate)
 values ('user9', n_seq.nextval, '11/01 공지사항','공지공지공지공지공지공지공지공지공지공지공지공지공','2021/11/01');
insert into notice_bbs (mid, nonum, notitle, nocontent, nodate)
 values ('user9', n_seq.nextval, '11/02 공지사항','공지공지공지공지공지공지공지공지공지공지공지공지공','2021/11/02');
insert into notice_bbs (mid, nonum, notitle, nocontent, nodate)
 values ('user9', n_seq.nextval, '11/03 공지사항','공지공지공지공지공지공지공지공지공지공지공지공지공','2021/11/03');
insert into notice_bbs (mid, nonum, notitle, nocontent, nodate)
 values ('user9', n_seq.nextval, '11/04 공지사항','공지공지공지공지공지공지공지공지공지공지공지공지공','2021/11/04');
insert into notice_bbs (mid, nonum, notitle, nocontent, nodate)
 values ('user9', n_seq.nextval, '11/05 공지사항','공지공지공지공지공지공지공지공지공지공지공지공지공','2021/11/05');
insert into notice_bbs (mid, nonum, notitle, nocontent, nodate)
 values ('user9', n_seq.nextval, '11/06 공지사항','공지공지공지공지공지공지공지공지공지공지공지공지공','2021/11/06');
insert into notice_bbs (mid, nonum, notitle, nocontent, nodate)
 values ('user10', n_seq.nextval, '11/07 공지사항','공지공지공지공지공지공지공지공지공지공지공지공지공','2021/11/07');
insert into notice_bbs (mid, nonum, notitle, nocontent, nodate)
 values ('user10', n_seq.nextval, '11/08 공지사항','공지공지공지공지공지공지공지공지공지공지공지공지공','2021/11/08');
insert into notice_bbs (mid, nonum, notitle, nocontent, nodate)
 values ('user10', n_seq.nextval, '11/09 공지사항','공지공지공지공지공지공지공지공지공지공지공지공지공','2021/11/09');
insert into notice_bbs (mid, nonum, notitle, nocontent, nodate)
 values ('user10', n_seq.nextval, '11/10 공지사항','공지공지공지공지공지공지공지공지공지공지공지공지공','2021/11/10');
insert into notice_bbs (mid, nonum, notitle, nocontent, nodate)
 values ('user10', n_seq.nextval, '11/11 공지사항','공지공지공지공지공지공지공지공지공지공지공지공지공','2021/11/11');
insert into notice_bbs (mid, nonum, notitle, nocontent, nodate)
 values ('user10', n_seq.nextval, '11/12 공지사항','공지공지공지공지공지공지공지공지공지공지공지공지공','2021/11/12');
 
 
 ---------- 구매자만 saleDB 에 들어감 ----------------
insert into sale (mid, pnum) values ('buyer',12);
insert into sale (mid, pnum) values ('user5',8);
insert into sale (mid, pnum) values ('user6',1);
insert into sale (mid, pnum) values ('user6',3);
insert into sale (mid, pnum) values ('buyer',4);
insert into sale (mid, pnum) values ('buyer',9);
insert into sale (mid, pnum) values ('user8',3);
insert into sale (mid, pnum) values ('user8',2);

select * from member;
select * from product;
select * from review;
select * from cart;
select * from sale;
select * from notice_bbs;
commit;

