/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     27/11/2014 13:56:38                          */
/*==============================================================*/


alter table ANSWER
   drop constraint FK_ANSWER_HASANSWER_QUESTION;

alter table PLAYERANSWER
   drop constraint FK_PLAYERAN_RELATIONS_PLAYER;

alter table PLAYERANSWER
   drop constraint FK_PLAYERAN_RELATIONS_ANSWER;

alter table PLAYERDUEL
   drop constraint FK_PLAYERDU_HAS_PLAYER;

alter table PLAYERDUEL
   drop constraint FK_PLAYERDU_HAS2_DUEL;

alter table QUESTION
   drop constraint FK_QUESTION_BELONGSTO_CATEGORY;

alter table ROUND
   drop constraint FK_ROUND_COMPOSEDB_DUEL;

alter table ROUND
   drop constraint FK_ROUND_HASCATEGO_CATEGORY;

alter table ROUNDQUESTION
   drop constraint FK_ROUNDQUE_USES_ROUND;

alter table ROUNDQUESTION
   drop constraint FK_ROUNDQUE_USES2_QUESTION;

drop index HASANSWER_FK;

drop table ANSWER cascade constraints;

drop table CATEGORY cascade constraints;

drop table DUEL cascade constraints;

drop table PLAYER cascade constraints;

drop index RELATIONSHIP_8_FK;

drop index RELATIONSHIP_7_FK;

drop table PLAYERANSWER cascade constraints;

drop index HAS2_FK;

drop index HAS_FK;

drop table PLAYERDUEL cascade constraints;

drop index BELONGSTO_FK;

drop table QUESTION cascade constraints;

drop index HASCATEGORY_FK;

drop index COMPOSEDBY_FK;

drop table ROUND cascade constraints;

drop index USES2_FK;

drop index USES_FK;

drop table ROUNDQUESTION cascade constraints;

/*==============================================================*/
/* Table: ANSWER                                                */
/*==============================================================*/
create table ANSWER  (
   ANSWERID             INTEGER                         not null,
   QUESTIONID           INTEGER                         not null,
   ANSWER               VARCHAR2(30)                    not null,
   CORRECT              SMALLINT                        not null,
   constraint PK_ANSWER primary key (ANSWERID)
);

/*==============================================================*/
/* Index: HASANSWER_FK                                          */
/*==============================================================*/
create index HASANSWER_FK on ANSWER (
   QUESTIONID ASC
);

/*==============================================================*/
/* Table: CATEGORY                                              */
/*==============================================================*/
create table CATEGORY  (
   CATEGORYNAME         VARCHAR2(40)                    not null,
   constraint PK_CATEGORY primary key (CATEGORYNAME)
);

/*==============================================================*/
/* Table: DUEL                                                  */
/*==============================================================*/
create table DUEL  (
   DUELID               INTEGER                         not null,
   STATUS               VARCHAR2(20)                    not null,
   SCOREPLAYER1         INTEGER                         not null,
   SCOREPLAYER2         INTEGER,
   constraint PK_DUEL primary key (DUELID)
);

/*==============================================================*/
/* Table: PLAYER                                                */
/*==============================================================*/
create table PLAYER  (
   USENAME              VARCHAR2(20)                    not null,
   PASSWORD             VARCHAR2(20)                    not null,
   STATE                VARCHAR2(20)                    not null,
   SCORE                INTEGER                         not null,
   constraint PK_PLAYER primary key (USENAME)
);

/*==============================================================*/
/* Table: PLAYERANSWER                                          */
/*==============================================================*/
create table PLAYERANSWER  (
   USENAME              VARCHAR2(20)                    not null,
   ANSWERID             INTEGER                         not null,
   constraint PK_PLAYERANSWER primary key (USENAME, ANSWERID)
);

/*==============================================================*/
/* Index: RELATIONSHIP_7_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_7_FK on PLAYERANSWER (
   USENAME ASC
);

/*==============================================================*/
/* Index: RELATIONSHIP_8_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_8_FK on PLAYERANSWER (
   ANSWERID ASC
);

/*==============================================================*/
/* Table: PLAYERDUEL                                            */
/*==============================================================*/
create table PLAYERDUEL  (
   USENAME              VARCHAR2(20)                    not null,
   DUELID               INTEGER                         not null,
   constraint PK_PLAYERDUEL primary key (USENAME, DUELID)
);

/*==============================================================*/
/* Index: HAS_FK                                                */
/*==============================================================*/
create index HAS_FK on PLAYERDUEL (
   USENAME ASC
);

/*==============================================================*/
/* Index: HAS2_FK                                               */
/*==============================================================*/
create index HAS2_FK on PLAYERDUEL (
   DUELID ASC
);

/*==============================================================*/
/* Table: QUESTION                                              */
/*==============================================================*/
create table QUESTION  (
   QUESTIONID           INTEGER                         not null,
   CATEGORYNAME         VARCHAR2(40)                    not null,
   QUESTION             VARCHAR2(100)                   not null,
   constraint PK_QUESTION primary key (QUESTIONID)
);

/*==============================================================*/
/* Index: BELONGSTO_FK                                          */
/*==============================================================*/
create index BELONGSTO_FK on QUESTION (
   CATEGORYNAME ASC
);

/*==============================================================*/
/* Table: ROUND                                                 */
/*==============================================================*/
create table ROUND  (
   DUELID               INTEGER                         not null,
   ROUNDID              INTEGER                         not null,
   CATEGORYNAME         VARCHAR2(40)                    not null,
   constraint PK_ROUND primary key (DUELID, ROUNDID)
);

/*==============================================================*/
/* Index: COMPOSEDBY_FK                                         */
/*==============================================================*/
create index COMPOSEDBY_FK on ROUND (
   DUELID ASC
);

/*==============================================================*/
/* Index: HASCATEGORY_FK                                        */
/*==============================================================*/
create index HASCATEGORY_FK on ROUND (
   CATEGORYNAME ASC
);

/*==============================================================*/
/* Table: ROUNDQUESTION                                         */
/*==============================================================*/
create table ROUNDQUESTION  (
   DUELID               INTEGER                         not null,
   ROUNDID              INTEGER                         not null,
   QUESTIONID           INTEGER                         not null,
   constraint PK_ROUNDQUESTION primary key (DUELID, ROUNDID, QUESTIONID)
);

/*==============================================================*/
/* Index: USES_FK                                               */
/*==============================================================*/
create index USES_FK on ROUNDQUESTION (
   DUELID ASC,
   ROUNDID ASC
);

/*==============================================================*/
/* Index: USES2_FK                                              */
/*==============================================================*/
create index USES2_FK on ROUNDQUESTION (
   QUESTIONID ASC
);

alter table ANSWER
   add constraint FK_ANSWER_HASANSWER_QUESTION foreign key (QUESTIONID)
      references QUESTION (QUESTIONID);

alter table PLAYERANSWER
   add constraint FK_PLAYERAN_RELATIONS_PLAYER foreign key (USENAME)
      references PLAYER (USENAME);

alter table PLAYERANSWER
   add constraint FK_PLAYERAN_RELATIONS_ANSWER foreign key (ANSWERID)
      references ANSWER (ANSWERID);

alter table PLAYERDUEL
   add constraint FK_PLAYERDU_HAS_PLAYER foreign key (USENAME)
      references PLAYER (USENAME);

alter table PLAYERDUEL
   add constraint FK_PLAYERDU_HAS2_DUEL foreign key (DUELID)
      references DUEL (DUELID);

alter table QUESTION
   add constraint FK_QUESTION_BELONGSTO_CATEGORY foreign key (CATEGORYNAME)
      references CATEGORY (CATEGORYNAME);

alter table ROUND
   add constraint FK_ROUND_COMPOSEDB_DUEL foreign key (DUELID)
      references DUEL (DUELID);

alter table ROUND
   add constraint FK_ROUND_HASCATEGO_CATEGORY foreign key (CATEGORYNAME)
      references CATEGORY (CATEGORYNAME);

alter table ROUNDQUESTION
   add constraint FK_ROUNDQUE_USES_ROUND foreign key (DUELID, ROUNDID)
      references ROUND (DUELID, ROUNDID);

alter table ROUNDQUESTION
   add constraint FK_ROUNDQUE_USES2_QUESTION foreign key (QUESTIONID)
      references QUESTION (QUESTIONID);

