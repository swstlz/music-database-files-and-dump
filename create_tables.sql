CREATE TABLE  Song ( Song_ID     INTEGER NOT NULL PRIMARY KEY,
                          S_NAME        VARCHAR(55) NOT NULL,
                          S_GENRE        CHAR(25) NOT NULL,
                          S_LENGTH       CHAR(10) NOT NULL,
                          S_LANGUAGE        VARCHAR(25) NOT NULL,
					      S_NUMOFSTREAMS INTEGER
                           );
CREATE TABLE Listener ( Listener_ID     INTEGER NOT NULL PRIMARY KEY,
                          U_name        VARCHAR(55) NOT NULL
                         
                           );
CREATE TABLE PlayList ( PL_ID     INTEGER NOT NULL PRIMARY KEY,
                             PL_NAME        CHAR(100) NOT NULL,
                             PL_NUMFOLLOW  integer not null
                            );
CREATE TABLE Album ( AL_ID     INTEGER NOT NULL PRIMARY KEY,
                             AL_NAME        CHAR(100) NOT NULL,
                             AL_RELEASEDATE  CHAR(25)
                            );
CREATE TABLE Artist( Art_ID     INTEGER NOT NULL PRIMARY KEY,
                             Art_NAME        CHAR(100) NOT NULL,
                             Art_NumFollower  integer not null
                            );
CREATE TABLE Album_Song( al_id     INTEGER NOT NULL,
						 song_id	INTEGER NOT NULL,
                         PRIMARY KEY (al_id,song_id)
                            );
CREATE TABLE Album_Artist( al_id     INTEGER NOT NULL ,
						   art_id	INTEGER NOT NULL ,
						  PRIMARY KEY (al_id,art_id)
                           
                            );
CREATE TABLE PlayList_Song( song_id     INTEGER NOT NULL,
						    Pl_id	INTEGER NOT NULL,
                         	PRIMARY KEY (song_id,Pl_id)
                            );
CREATE TABLE listener_create_playlist( listener_id     INTEGER NOT NULL ,
						    Pl_id	INTEGER NOT NULL,
                            PRIMARY KEY (listener_id,Pl_id)
                            );
CREATE TABLE listener_follow_playlist( listener_id     INTEGER NOT NULL ,
						    Pl_id	INTEGER NOT NULL,
                            PRIMARY KEY (listener_id,Pl_id)
                            );
