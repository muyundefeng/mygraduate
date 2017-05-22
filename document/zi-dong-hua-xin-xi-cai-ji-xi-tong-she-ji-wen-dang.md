# 数据库设计

channel表格设计，主要存放渠道信息

| 字段 | 类型 | 大小 | 备注 | 是否为主键 |
| :--- | :--- | :--- | :--- | :--- |
| channle\_Id | int | 255 | 渠道编号 | 是 |
| seed\_url | varchar | 512 | 种子连接 | 否 |
| channel\_name | varchar | 512 | 渠道名称 | 否 |
| regex | varchar | 512 | 生成渠道正则表达式 | 否 |

sql语句：

```
CREATE TABLE IF NOT EXISTS channel (
  channle_Id   INT(255) PRIMARY KEY NOT NULL,
  seed_url     VARCHAR(512)         NOT NULL,
  channel_name VARCHAR(512)         NOT NULL,
  regex        VARCHAR(512)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;
```

htmls表格设计，主要存储爬取的html页面和已经处理过的页面

| 字段 | 类型 | 大小 | 备注 | 是否为主键 |
| :--- | :--- | :--- | :--- | :--- |
| channel\_id | int | 255 | 渠道编号 | 否 |
| url | varchar | 512 | 该网页对应的url | 否 |
| content | longtext |  | html页面 | 否 |
| processContent | longtext |  | 处理过的html页面 | 否 |

sql语句如下：

```
CREATE TABLE IF NOT EXISTS htmls (
  channel_id     INT(255)     NOT NULL,
  url            VARCHAR(512) NOT NULL,
  content        LONGTEXT,
    processContent LONGTEXT
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;
```

cluster主要用于存放聚类的相关信息

| 字段 | 类型 | 大小 | 备注 | 是否为主键 |
| :--- | :--- | :--- | :--- | :--- |
| id | varchar | 255 | 主键 | 是 |
| cluster\_id | varchar | 255 | 聚类编号 | 否 |
| url | varchar | 255 | 该链接的url | 否 |

sql语句如下：

```
CREATE TABLE IF NOT EXISTS CLUSTER (
  id         VARCHAR(255) PRIMARY KEY NOT NULL,
  cluster_id VARCHAR(255)             NOT NULL,
  url        VARCHAR(255)             NOT NULL
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;
```



