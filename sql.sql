-- create table `channel`
CREATE TABLE IF NOT EXISTS channel (
    channle_Id int(255) PRIMARY KEY NOT NULL,
    seed_url VARCHAR(512) NOT NULL,
    channel_name VARCHAR(512) NOT NULL,
    regex VARCHAR(512)
) ENGINE=INNODB DEFAULT CHARSET=utf8;