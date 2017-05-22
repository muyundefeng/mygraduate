# 工程介绍

#### 工程构建

本工程使用gradle构建的多项目工程，但是不存在gradle层面的项目的依赖，纯粹是将多个项目集中到一个工程中。

#### 工程目录

graduateDesign

--**cluster**

--**extractor**

--**logAnalyser**

--**spider**

--**webShow**

--log

--url

--urls

--afterUrls

--html

如上面所示基本的目录结构，加粗的表示五个基本的项目，其中cluster是url聚类的主要代码，聚类的输入是urls，输出是afterUrls.extractor是正则表达式生成以及网页信息提取的核心代码，也是整个工程的核心代码。logAnlyser是简单的日志分析模块，提取日志中的url链接，存放到url目录,输出是log目录之下的spider。spider就是一个简单的爬虫框架。webShow是前端展示页面，打算使用SpringMVC构建

