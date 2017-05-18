USE auto_spider;
-- create table `channel`
CREATE TABLE IF NOT EXISTS channel (
  channle_Id   INT(255) PRIMARY KEY NOT NULL,
  seed_url     VARCHAR(512)         NOT NULL,
  channel_name VARCHAR(512)         NOT NULL,
  regex        VARCHAR(512)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS htmls (
  channel_id     INT(255)     NOT NULL,
  url            VARCHAR(512) NOT NULL,
  content        LONGTEXT,
  processContent LONGTEXT
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS CLUSTER (
  id         VARCHAR(255) PRIMARY KEY NOT NULL,
  cluster_id VARCHAR(255)             NOT NULL,
  url        VARCHAR(255)             NOT NULL
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;