create table spotify_youtube(
        PK_ID bigint(20) primary key auto_increment,
        ARTIST varchar(255),
        TITLE varchar(255),
        YOUTUBE_URL varchar(4000),
        PROCESSED char(1));