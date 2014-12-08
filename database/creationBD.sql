--------------------------------------------------------
--  Fichier créé - lundi-décembre-08-2014   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ANSWER
--------------------------------------------------------

  CREATE TABLE "ANSWER" 
   (	"ANSWERID" NUMBER(*,0), 
	"QUESTIONID" NUMBER(*,0), 
	"ANSWER" VARCHAR2(30 BYTE), 
	"CORRECT" NUMBER(*,0)
   ) ;
/
--------------------------------------------------------
--  DDL for Table CATEGORY
--------------------------------------------------------

  CREATE TABLE "CATEGORY" 
   (	"CATEGORYNAME" VARCHAR2(40 BYTE)
   ) ;
/
--------------------------------------------------------
--  DDL for Table DUEL
--------------------------------------------------------

  CREATE TABLE "DUEL" 
   (	"DUELID" NUMBER(*,0), 
	"STATUS" VARCHAR2(20 BYTE), 
	"SCOREPLAYER1" NUMBER(*,0), 
	"SCOREPLAYER2" NUMBER(*,0), 
	"TURN" VARCHAR2(20 BYTE)
   ) ;
/
--------------------------------------------------------
--  DDL for Table PLAYER
--------------------------------------------------------

  CREATE TABLE "PLAYER" 
   (	"USERNAME" VARCHAR2(20 BYTE), 
	"PASSWORD" VARCHAR2(20 BYTE), 
	"STATE" VARCHAR2(20 BYTE), 
	"SCORE" NUMBER(*,0)
   ) ;
/
--------------------------------------------------------
--  DDL for Table PLAYERANSWER
--------------------------------------------------------

  CREATE TABLE "PLAYERANSWER" 
   (	"USERNAME" VARCHAR2(20 BYTE), 
	"ANSWERID" NUMBER(*,0), 
	"DUELID" NUMBER(*,0), 
	"ROUNDID" NUMBER(*,0)
   ) ;
/
--------------------------------------------------------
--  DDL for Table PLAYERDUEL
--------------------------------------------------------

  CREATE TABLE "PLAYERDUEL" 
   (	"USERNAME" VARCHAR2(20 BYTE), 
	"DUELID" NUMBER(*,0)
   ) ;
/
--------------------------------------------------------
--  DDL for Table QUESTION
--------------------------------------------------------

  CREATE TABLE "QUESTION" 
   (	"QUESTIONID" NUMBER(*,0), 
	"CATEGORYNAME" VARCHAR2(40 BYTE), 
	"QUESTION" VARCHAR2(100 BYTE)
   ) ;
/
--------------------------------------------------------
--  DDL for Table ROUND
--------------------------------------------------------

  CREATE TABLE "ROUND" 
   (	"DUELID" NUMBER(*,0), 
	"ROUNDID" NUMBER(*,0), 
	"CATEGORYNAME" VARCHAR2(40 BYTE), 
	"P1HASPLAYED" NUMBER(1,0) DEFAULT 0, 
	"P2HASPLAYED" NUMBER(1,0) DEFAULT 0
   ) ;
/
--------------------------------------------------------
--  DDL for Table ROUNDQUESTION
--------------------------------------------------------

  CREATE TABLE "ROUNDQUESTION" 
   (	"DUELID" NUMBER(*,0), 
	"ROUNDID" NUMBER(*,0), 
	"QUESTIONID" NUMBER(*,0)
   ) ;
/
REM INSERTING into ANSWER
SET DEFINE OFF;
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('1','1','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('2','1','Answer 2','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('3','1','Answer 3','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('4','1','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('5','15','Answer 4','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('6','2','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('7','2','Answer 2','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('8','2','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('9','2','Answer 4','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('10','3','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('11','3','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('12','3','Answer 3','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('13','3','Answer 4','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('14','4','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('15','4','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('16','4','Answer 3','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('17','4','Answer 4','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('18','5','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('19','5','Answer 2','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('20','5','Answer 3','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('21','5','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('22','6','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('23','6','Answer 2','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('24','6','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('25','6','Answer 4','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('26','7','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('27','7','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('28','7','Answer 3','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('29','7','Answer 4','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('30','8','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('31','8','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('32','8','Answer 3','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('33','8','Answer 4','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('34','9','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('35','9','Answer 2','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('36','9','Answer 3','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('37','9','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('38','10','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('39','10','Answer 2','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('40','10','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('41','10','Answer 4','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('42','11','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('43','11','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('44','11','Answer 3','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('45','11','Answer 4','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('46','12','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('47','12','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('48','12','Answer 3','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('49','12','Answer 4','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('50','13','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('51','13','Answer 2','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('52','13','Answer 3','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('53','13','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('54','14','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('55','14','Answer 2','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('56','14','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('57','14','Answer 4','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('58','15','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('59','15','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('60','15','Answer 3','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('61','16','Correct','1');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('62','16','Answer 1','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('63','16','Answer 3','0');
Insert into ANSWER (ANSWERID,QUESTIONID,ANSWER,CORRECT) values ('64','16','Answer 4','0');
REM INSERTING into CATEGORY
SET DEFINE OFF;
Insert into CATEGORY (CATEGORYNAME) values ('Art et culture');
Insert into CATEGORY (CATEGORYNAME) values ('BD et cartoons');
Insert into CATEGORY (CATEGORYNAME) values ('Cinéma');
Insert into CATEGORY (CATEGORYNAME) values ('Consoles et Jeux-vidéo');
Insert into CATEGORY (CATEGORYNAME) values ('Corps et âme');
Insert into CATEGORY (CATEGORYNAME) values ('Croyance et superstition');
Insert into CATEGORY (CATEGORYNAME) values ('Dans le laboratoire');
Insert into CATEGORY (CATEGORYNAME) values ('Divertissement et média');
Insert into CATEGORY (CATEGORYNAME) values ('La Faune et la flore');
Insert into CATEGORY (CATEGORYNAME) values ('Le 21-ème siècle');
Insert into CATEGORY (CATEGORYNAME) values ('Les belles-lettres');
Insert into CATEGORY (CATEGORYNAME) values ('Musique et hits');
Insert into CATEGORY (CATEGORYNAME) values ('Sport');
Insert into CATEGORY (CATEGORYNAME) values ('Séries TV');
Insert into CATEGORY (CATEGORYNAME) values ('Technologie');
REM INSERTING into DUEL
SET DEFINE OFF;
Insert into DUEL (DUELID,STATUS,SCOREPLAYER1,SCOREPLAYER2,TURN) values ('46','Fini','5','8','earojasc');
Insert into DUEL (DUELID,STATUS,SCOREPLAYER1,SCOREPLAYER2,TURN) values ('31','Fini','8','9','earojasc');
Insert into DUEL (DUELID,STATUS,SCOREPLAYER1,SCOREPLAYER2,TURN) values ('41','En cours','1','0','Earojas');
Insert into DUEL (DUELID,STATUS,SCOREPLAYER1,SCOREPLAYER2,TURN) values ('36','En Attente','0','0','sergio');
REM INSERTING into PLAYER
SET DEFINE OFF;
Insert into PLAYER (USERNAME,PASSWORD,STATE,SCORE) values ('user34','pqss34','UNAVAILABLE','0');
Insert into PLAYER (USERNAME,PASSWORD,STATE,SCORE) values ('Jmmr','1234','AVAILABLE','0');
Insert into PLAYER (USERNAME,PASSWORD,STATE,SCORE) values ('sergio','sergio','AVAILABLE','9999');
Insert into PLAYER (USERNAME,PASSWORD,STATE,SCORE) values ('user19','pass19','UNAVAILABLE','8');
Insert into PLAYER (USERNAME,PASSWORD,STATE,SCORE) values ('earojasc','pass','UNAVAILABLE','5');
Insert into PLAYER (USERNAME,PASSWORD,STATE,SCORE) values ('Jmmr1','Jmmr1','UNAVAILABLE','0');
Insert into PLAYER (USERNAME,PASSWORD,STATE,SCORE) values ('Earojas','1234','UNAVAILABLE','0');
Insert into PLAYER (USERNAME,PASSWORD,STATE,SCORE) values ('user20','pass20','UNAVAILABLE','0');
Insert into PLAYER (USERNAME,PASSWORD,STATE,SCORE) values ('User21','Pass21','UNAVAILABLE','0');
REM INSERTING into PLAYERANSWER
SET DEFINE OFF;
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','1','31','2');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','1','31','3');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','1','31','4');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','1','31','5');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','1','46','1');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','1','46','2');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','2','31','1');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','2','31','4');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','2','46','4');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','2','46','6');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','3','31','6');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','3','46','3');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','3','46','4');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','4','31','1');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','4','31','2');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','4','31','3');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','4','31','5');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','4','31','6');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','4','46','1');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','4','46','2');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','4','46','5');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','4','46','6');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','5','31','2');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','5','46','1');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','5','46','3');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','5','46','4');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('earojasc','5','46','5');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','1','31','1');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','1','31','5');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','1','41','1');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','2','31','4');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','3','31','1');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','3','31','3');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','3','31','6');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','3','41','1');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','4','31','2');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','4','31','3');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','4','31','4');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','4','31','5');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','4','31','6');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','4','41','1');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','5','31','1');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('sergio','5','31','4');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','1','46','2');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','1','46','3');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','1','46','4');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','1','46','5');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','1','46','6');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','2','46','4');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','4','46','1');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','4','46','2');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','4','46','3');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','4','46','4');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','4','46','5');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','4','46','6');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','5','46','2');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','5','46','3');
Insert into PLAYERANSWER (USERNAME,ANSWERID,DUELID,ROUNDID) values ('user19','5','46','5');
REM INSERTING into PLAYERDUEL
SET DEFINE OFF;
Insert into PLAYERDUEL (USERNAME,DUELID) values ('Earojas','41');
Insert into PLAYERDUEL (USERNAME,DUELID) values ('earojasc','31');
Insert into PLAYERDUEL (USERNAME,DUELID) values ('earojasc','36');
Insert into PLAYERDUEL (USERNAME,DUELID) values ('earojasc','46');
Insert into PLAYERDUEL (USERNAME,DUELID) values ('sergio','31');
Insert into PLAYERDUEL (USERNAME,DUELID) values ('sergio','36');
Insert into PLAYERDUEL (USERNAME,DUELID) values ('sergio','41');
Insert into PLAYERDUEL (USERNAME,DUELID) values ('user19','46');
REM INSERTING into QUESTION
SET DEFINE OFF;
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('1','Art et culture','Question 1 Art et culture');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('2','Art et culture','Question 2 Art et culture');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('3','Art et culture','Question 3 Art et culture');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('4','Art et culture','Question 4 Art et culture');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('5','BD et cartoons','Question 1 BD et cartoons');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('6','BD et cartoons','Question 2 BD et cartoons');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('7','BD et cartoons','Question 3 BD et cartoons');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('8','BD et cartoons','Question 4 BD et cartoons');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('9','Cinéma','Question 1 Cinéma');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('10','Cinéma','Question 2 Cinéma');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('11','Cinéma','Question 3 Cinéma');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('12','Cinéma','Question 4 Cinéma');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('13','Consoles et Jeux-vidéo','Question 1 Consoles et Jeux-vidéo');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('14','Consoles et Jeux-vidéo','Question 2 Consoles et Jeux-vidéo');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('15','Consoles et Jeux-vidéo','Question 3 Consoles et Jeux-vidéo');
Insert into QUESTION (QUESTIONID,CATEGORYNAME,QUESTION) values ('16','Consoles et Jeux-vidéo','Question 4 Consoles et Jeux-vidéo');
REM INSERTING into ROUND
SET DEFINE OFF;
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('46','3','BD et cartoons','1','1');
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('31','5','Cinéma','1','1');
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('46','4','Consoles et Jeux-vidéo','1','1');
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('46','5','Consoles et Jeux-vidéo','1','1');
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('46','6','BD et cartoons','1','1');
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('46','1','Consoles et Jeux-vidéo','1','1');
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('31','4','BD et cartoons','1','1');
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('31','6','BD et cartoons','1','1');
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('31','1','Consoles et Jeux-vidéo','1','1');
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('46','2','Cinéma','1','1');
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('31','3','Cinéma','1','1');
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('41','1','Consoles et Jeux-vidéo','1','0');
Insert into ROUND (DUELID,ROUNDID,CATEGORYNAME,P1HASPLAYED,P2HASPLAYED) values ('31','2','BD et cartoons','1','1');
REM INSERTING into ROUNDQUESTION
SET DEFINE OFF;
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','1','13');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','1','14');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','1','16');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','2','5');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','2','7');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','2','8');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','3','9');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','3','10');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','3','11');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','4','5');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','4','7');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','4','8');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','5','10');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','5','11');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','5','12');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','6','5');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','6','7');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('31','6','8');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('41','1','13');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('41','1','15');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('41','1','16');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','1','13');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','1','14');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','1','16');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','2','9');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','2','10');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','2','11');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','3','5');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','3','6');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','3','8');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','4','14');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','4','15');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','4','16');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','5','14');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','5','15');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','5','16');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','6','5');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','6','6');
Insert into ROUNDQUESTION (DUELID,ROUNDID,QUESTIONID) values ('46','6','8');
--------------------------------------------------------
--  DDL for Index PK_ANSWER
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ANSWER" ON "ANSWER" ("ANSWERID") 
  ;
/
--------------------------------------------------------
--  DDL for Index HASANSWER_FK
--------------------------------------------------------

  CREATE INDEX "HASANSWER_FK" ON "ANSWER" ("QUESTIONID") 
  ;
/
--------------------------------------------------------
--  DDL for Index PK_CATEGORY
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CATEGORY" ON "CATEGORY" ("CATEGORYNAME") 
  ;
/
--------------------------------------------------------
--  DDL for Index HASTURN_FK
--------------------------------------------------------

  CREATE INDEX "HASTURN_FK" ON "DUEL" ("TURN") 
  ;
/
--------------------------------------------------------
--  DDL for Index PK_DUEL
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_DUEL" ON "DUEL" ("DUELID") 
  ;
/
--------------------------------------------------------
--  DDL for Index PK_PLAYER
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PLAYER" ON "PLAYER" ("USERNAME") 
  ;
/
--------------------------------------------------------
--  DDL for Index PK_PLAYERANSWER
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PLAYERANSWER" ON "PLAYERANSWER" ("USERNAME", "ANSWERID", "DUELID", "ROUNDID") 
  ;
/
--------------------------------------------------------
--  DDL for Index PK_PLAYERDUEL
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PLAYERDUEL" ON "PLAYERDUEL" ("USERNAME", "DUELID") 
  ;
/
--------------------------------------------------------
--  DDL for Index HAS_FK
--------------------------------------------------------

  CREATE INDEX "HAS_FK" ON "PLAYERDUEL" ("USERNAME") 
  ;
/
--------------------------------------------------------
--  DDL for Index HAS2_FK
--------------------------------------------------------

  CREATE INDEX "HAS2_FK" ON "PLAYERDUEL" ("DUELID") 
  ;
/
--------------------------------------------------------
--  DDL for Index PK_QUESTION
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_QUESTION" ON "QUESTION" ("QUESTIONID") 
  ;
/
--------------------------------------------------------
--  DDL for Index BELONGSTO_FK
--------------------------------------------------------

  CREATE INDEX "BELONGSTO_FK" ON "QUESTION" ("CATEGORYNAME") 
  ;
/
--------------------------------------------------------
--  DDL for Index PK_ROUND
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ROUND" ON "ROUND" ("DUELID", "ROUNDID") 
  ;
/
--------------------------------------------------------
--  DDL for Index COMPOSEDBY_FK
--------------------------------------------------------

  CREATE INDEX "COMPOSEDBY_FK" ON "ROUND" ("DUELID") 
  ;
/
--------------------------------------------------------
--  DDL for Index HASCATEGORY_FK
--------------------------------------------------------

  CREATE INDEX "HASCATEGORY_FK" ON "ROUND" ("CATEGORYNAME") 
  ;
/
--------------------------------------------------------
--  DDL for Index PK_ROUNDQUESTION
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ROUNDQUESTION" ON "ROUNDQUESTION" ("DUELID", "ROUNDID", "QUESTIONID") 
  ;
/
--------------------------------------------------------
--  DDL for Index USES_FK
--------------------------------------------------------

  CREATE INDEX "USES_FK" ON "ROUNDQUESTION" ("DUELID", "ROUNDID") 
  ;
/
--------------------------------------------------------
--  DDL for Index USES2_FK
--------------------------------------------------------

  CREATE INDEX "USES2_FK" ON "ROUNDQUESTION" ("QUESTIONID") 
  ;
/
--------------------------------------------------------
--  Constraints for Table ANSWER
--------------------------------------------------------

  ALTER TABLE "ANSWER" MODIFY ("CORRECT" NOT NULL ENABLE);
  ALTER TABLE "ANSWER" MODIFY ("ANSWER" NOT NULL ENABLE);
  ALTER TABLE "ANSWER" MODIFY ("QUESTIONID" NOT NULL ENABLE);
  ALTER TABLE "ANSWER" MODIFY ("ANSWERID" NOT NULL ENABLE);
  ALTER TABLE "ANSWER" ADD CONSTRAINT "PK_ANSWER" PRIMARY KEY ("ANSWERID")
  USING INDEX  ENABLE;
/
--------------------------------------------------------
--  Constraints for Table CATEGORY
--------------------------------------------------------

  ALTER TABLE "CATEGORY" MODIFY ("CATEGORYNAME" NOT NULL ENABLE);
  ALTER TABLE "CATEGORY" ADD CONSTRAINT "PK_CATEGORY" PRIMARY KEY ("CATEGORYNAME")
  USING INDEX  ENABLE;
/
--------------------------------------------------------
--  Constraints for Table DUEL
--------------------------------------------------------

  ALTER TABLE "DUEL" MODIFY ("SCOREPLAYER1" NOT NULL ENABLE);
  ALTER TABLE "DUEL" MODIFY ("STATUS" NOT NULL ENABLE);
  ALTER TABLE "DUEL" MODIFY ("DUELID" NOT NULL ENABLE);
  ALTER TABLE "DUEL" ADD CONSTRAINT "PK_DUEL" PRIMARY KEY ("DUELID")
  USING INDEX  ENABLE;
/
--------------------------------------------------------
--  Constraints for Table PLAYER
--------------------------------------------------------

  ALTER TABLE "PLAYER" MODIFY ("SCORE" NOT NULL ENABLE);
  ALTER TABLE "PLAYER" MODIFY ("STATE" NOT NULL ENABLE);
  ALTER TABLE "PLAYER" MODIFY ("PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "PLAYER" MODIFY ("USERNAME" NOT NULL ENABLE);
  ALTER TABLE "PLAYER" ADD CONSTRAINT "PK_PLAYER" PRIMARY KEY ("USERNAME")
  USING INDEX  ENABLE;
/
--------------------------------------------------------
--  Constraints for Table PLAYERANSWER
--------------------------------------------------------

  ALTER TABLE "PLAYERANSWER" MODIFY ("ROUNDID" NOT NULL ENABLE);
  ALTER TABLE "PLAYERANSWER" MODIFY ("DUELID" NOT NULL ENABLE);
  ALTER TABLE "PLAYERANSWER" MODIFY ("ANSWERID" NOT NULL ENABLE);
  ALTER TABLE "PLAYERANSWER" MODIFY ("USERNAME" NOT NULL ENABLE);
  ALTER TABLE "PLAYERANSWER" ADD CONSTRAINT "PK_PLAYERANSWER" PRIMARY KEY ("USERNAME", "ANSWERID", "DUELID", "ROUNDID")
  USING INDEX  ENABLE;
/
--------------------------------------------------------
--  Constraints for Table PLAYERDUEL
--------------------------------------------------------

  ALTER TABLE "PLAYERDUEL" MODIFY ("DUELID" NOT NULL ENABLE);
  ALTER TABLE "PLAYERDUEL" MODIFY ("USERNAME" NOT NULL ENABLE);
  ALTER TABLE "PLAYERDUEL" ADD CONSTRAINT "PK_PLAYERDUEL" PRIMARY KEY ("USERNAME", "DUELID")
  USING INDEX  ENABLE;
/
--------------------------------------------------------
--  Constraints for Table QUESTION
--------------------------------------------------------

  ALTER TABLE "QUESTION" MODIFY ("QUESTION" NOT NULL ENABLE);
  ALTER TABLE "QUESTION" MODIFY ("CATEGORYNAME" NOT NULL ENABLE);
  ALTER TABLE "QUESTION" MODIFY ("QUESTIONID" NOT NULL ENABLE);
  ALTER TABLE "QUESTION" ADD CONSTRAINT "PK_QUESTION" PRIMARY KEY ("QUESTIONID")
  USING INDEX  ENABLE;
/
--------------------------------------------------------
--  Constraints for Table ROUND
--------------------------------------------------------

  ALTER TABLE "ROUND" MODIFY ("P2HASPLAYED" NOT NULL ENABLE);
  ALTER TABLE "ROUND" MODIFY ("P1HASPLAYED" NOT NULL ENABLE);
  ALTER TABLE "ROUND" MODIFY ("CATEGORYNAME" NOT NULL ENABLE);
  ALTER TABLE "ROUND" MODIFY ("ROUNDID" NOT NULL ENABLE);
  ALTER TABLE "ROUND" MODIFY ("DUELID" NOT NULL ENABLE);
  ALTER TABLE "ROUND" ADD CONSTRAINT "PK_ROUND" PRIMARY KEY ("DUELID", "ROUNDID")
  USING INDEX  ENABLE;
/
--------------------------------------------------------
--  Constraints for Table ROUNDQUESTION
--------------------------------------------------------

  ALTER TABLE "ROUNDQUESTION" MODIFY ("QUESTIONID" NOT NULL ENABLE);
  ALTER TABLE "ROUNDQUESTION" MODIFY ("ROUNDID" NOT NULL ENABLE);
  ALTER TABLE "ROUNDQUESTION" MODIFY ("DUELID" NOT NULL ENABLE);
  ALTER TABLE "ROUNDQUESTION" ADD CONSTRAINT "PK_ROUNDQUESTION" PRIMARY KEY ("DUELID", "ROUNDID", "QUESTIONID")
  USING INDEX  ENABLE;
/
--------------------------------------------------------
--  Ref Constraints for Table ANSWER
--------------------------------------------------------

  ALTER TABLE "ANSWER" ADD CONSTRAINT "FK_ANSWER_HASANSWER_QUESTION" FOREIGN KEY ("QUESTIONID")
	  REFERENCES "QUESTION" ("QUESTIONID") ENABLE;
/
--------------------------------------------------------
--  Ref Constraints for Table DUEL
--------------------------------------------------------

  ALTER TABLE "DUEL" ADD CONSTRAINT "FK_PLAYER_HASTURN_DUEL" FOREIGN KEY ("TURN")
	  REFERENCES "PLAYER" ("USERNAME") ENABLE;
/
--------------------------------------------------------
--  Ref Constraints for Table PLAYERANSWER
--------------------------------------------------------

  ALTER TABLE "PLAYERANSWER" ADD CONSTRAINT "FK_PLAYERAN_RELATIONS_ANSWER" FOREIGN KEY ("ANSWERID")
	  REFERENCES "ANSWER" ("ANSWERID") ENABLE;
  ALTER TABLE "PLAYERANSWER" ADD CONSTRAINT "FK_PLAYERAN_RELATIONS_PLAYER" FOREIGN KEY ("USERNAME")
	  REFERENCES "PLAYER" ("USERNAME") ENABLE;
  ALTER TABLE "PLAYERANSWER" ADD CONSTRAINT "FK_PLAYERAN_RELATION_ROUND" FOREIGN KEY ("DUELID", "ROUNDID")
	  REFERENCES "ROUND" ("DUELID", "ROUNDID") ENABLE;
/
--------------------------------------------------------
--  Ref Constraints for Table PLAYERDUEL
--------------------------------------------------------

  ALTER TABLE "PLAYERDUEL" ADD CONSTRAINT "FK_PLAYERDU_HAS2_DUEL" FOREIGN KEY ("DUELID")
	  REFERENCES "DUEL" ("DUELID") ENABLE;
  ALTER TABLE "PLAYERDUEL" ADD CONSTRAINT "FK_PLAYERDU_HAS_PLAYER" FOREIGN KEY ("USERNAME")
	  REFERENCES "PLAYER" ("USERNAME") ENABLE;
/
--------------------------------------------------------
--  Ref Constraints for Table QUESTION
--------------------------------------------------------

  ALTER TABLE "QUESTION" ADD CONSTRAINT "FK_QUESTION_BELONGSTO_CATEGORY" FOREIGN KEY ("CATEGORYNAME")
	  REFERENCES "CATEGORY" ("CATEGORYNAME") ENABLE;
/
--------------------------------------------------------
--  Ref Constraints for Table ROUND
--------------------------------------------------------

  ALTER TABLE "ROUND" ADD CONSTRAINT "FK_ROUND_COMPOSEDB_DUEL" FOREIGN KEY ("DUELID")
	  REFERENCES "DUEL" ("DUELID") ENABLE;
  ALTER TABLE "ROUND" ADD CONSTRAINT "FK_ROUND_HASCATEGO_CATEGORY" FOREIGN KEY ("CATEGORYNAME")
	  REFERENCES "CATEGORY" ("CATEGORYNAME") ENABLE;
/
--------------------------------------------------------
--  Ref Constraints for Table ROUNDQUESTION
--------------------------------------------------------

  ALTER TABLE "ROUNDQUESTION" ADD CONSTRAINT "FK_ROUNDQUE_USES2_QUESTION" FOREIGN KEY ("QUESTIONID")
	  REFERENCES "QUESTION" ("QUESTIONID") ENABLE;
  ALTER TABLE "ROUNDQUESTION" ADD CONSTRAINT "FK_ROUNDQUE_USES_ROUND" FOREIGN KEY ("DUELID", "ROUNDID")
	  REFERENCES "ROUND" ("DUELID", "ROUNDID") ENABLE;
/
