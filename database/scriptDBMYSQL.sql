/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     05/12/2014 23:47:22                          */
/*==============================================================*/


drop table if exists PLAYERANSWER;

drop table if exists ANSWER;

drop table if exists ROUNDQUESTION;

drop table if exists QUESTION;

drop table if exists ROUND;

drop table if exists CATEGORY;

drop table if exists PLAYERDUEL;

drop table if exists DUEL;

drop table if exists PLAYER;



/*==============================================================*/
/* Table: ANSWER                                                */
/*==============================================================*/
create table ANSWER
(
   ANSWERID             int not null AUTO_INCREMENT,
   QUESTIONID           int not null,
   ANSWER               varchar(30) not null,
   CORRECT              bool not null,
   primary key (ANSWERID)
);

/*==============================================================*/
/* Table: CATEGORY                                              */
/*==============================================================*/
create table CATEGORY
(
   CATEGORYNAME         varchar(40) not null,
   primary key (CATEGORYNAME)
);

/*==============================================================*/
/* Table: DUEL                                                  */
/*==============================================================*/
create table DUEL
(
   DUELID               int not null AUTO_INCREMENT,
   STATUS               varchar(20) not null,
   SCOREPLAYER1         int not null,
   SCOREPLAYER2         int,
   ACTIVEUSER		varchar(20) not null,
   primary key (DUELID)
);

/*==============================================================*/
/* Table: PLAYER                                                */
/*==============================================================*/
create table PLAYER
(
   USERNAME             varchar(20) not null,
   PASSWORD             varchar(20) not null,
   STATE                varchar(20) not null,
   SCORE                int not null,
   primary key (USERNAME)
);

/*==============================================================*/
/* Table: PLAYERANSWER                                          */
/*==============================================================*/
create table PLAYERANSWER
(
   USERNAME             varchar(20) not null,
   ANSWERID             int not null,
   primary key (USERNAME, ANSWERID)
);

/*==============================================================*/
/* Table: PLAYERDUEL                                            */
/*==============================================================*/
create table PLAYERDUEL
(
   USERNAME             varchar(20) not null,
   DUELID               int not null,
   primary key (USERNAME, DUELID)
);

/*==============================================================*/
/* Table: QUESTION                                              */
/*==============================================================*/
create table QUESTION
(
   QUESTIONID           int not null AUTO_INCREMENT,
   CATEGORYNAME         varchar(40) not null,
   QUESTION             varchar(100) not null,
   primary key (QUESTIONID)
);

/*==============================================================*/
/* Table: ROUND                                                 */
/*==============================================================*/
create table ROUND
(
   DUELID               int not null,
   ROUNDID              int not null,
   P1HASPLAYED          bool not null,
   P2HASPLAYED          bool not null,
   CATEGORYNAME         varchar(40) not null,
   primary key (DUELID, ROUNDID)
);

/*==============================================================*/
/* Table: ROUNDQUESTION                                         */
/*==============================================================*/
create table ROUNDQUESTION
(
   DUELID               int not null,
   ROUNDID              int not null,
   QUESTIONID           int not null,
   primary key (DUELID, ROUNDID, QUESTIONID)
);

alter table ANSWER add constraint FK_HASANSWER foreign key (QUESTIONID)
      references QUESTION (QUESTIONID) on delete restrict on update restrict;

alter table PLAYERANSWER add constraint FK_CHOISES foreign key (USERNAME)
      references PLAYER (USERNAME) on delete restrict on update restrict;

alter table PLAYERANSWER add constraint FK_CHOISES2 foreign key (ANSWERID)
      references ANSWER (ANSWERID) on delete restrict on update restrict;

alter table PLAYERDUEL add constraint FK_HAS foreign key (USERNAME)
      references PLAYER (USERNAME) on delete restrict on update restrict;

alter table PLAYERDUEL add constraint FK_HAS2 foreign key (DUELID)
      references DUEL (DUELID) on delete restrict on update restrict;

alter table QUESTION add constraint FK_BELONGSTO foreign key (CATEGORYNAME)
      references CATEGORY (CATEGORYNAME) on delete restrict on update restrict;

alter table ROUND add constraint FK_COMPOSEDBY foreign key (DUELID)
      references DUEL (DUELID) on delete restrict on update restrict;

alter table ROUND add constraint FK_HASCATEGORY foreign key (CATEGORYNAME)
      references CATEGORY (CATEGORYNAME) on delete restrict on update restrict;

alter table ROUNDQUESTION add constraint FK_USES foreign key (DUELID, ROUNDID)
      references ROUND (DUELID, ROUNDID) on delete restrict on update restrict;

alter table ROUNDQUESTION add constraint FK_USES2 foreign key (QUESTIONID)
      references QUESTION (QUESTIONID) on delete restrict on update restrict;
	  
alter table DUEL add constraint FK_ACTIVEUSER foreign key (ACTIVEUSER) 
	  references PLAYER (USERNAME) on delete restrict on update restrict;
