#  zealsay 
------
 ![beta 1.0.0](https://img.shields.io/badge/beta-1.0.0-ff69b4.svg)
![spring boot 2.0.4](https://img.shields.io/badge/spring%20boot-2.0.4-green.svg) ![swagger valid ](https://img.shields.io/badge/swagger-valid-brightgreen.svg) ![License MPL-2.0](https://img.shields.io/badge/license-MPL--2.0-green.svg)  [![Build Status](https://travis-ci.org/GodLikeZeal/zealsay_backend.svg?branch=master)](https://travis-ci.org/GodLikeZeal/zealsay_backend) [![](https://img.shields.io/docker/stars/zealsay/zealsay_backend.svg)](https://hub.docker.com/r/zealsay/zealsay_backend 'DockerHub') [![](https://img.shields.io/docker/pulls/zealsay/zealsay_backend.svg)](https://hub.docker.com/r/zealsay/zealsay_backend 'DockerHub')
## 开发指南
请参考 [开发文档](https://www.zealsay.com)
## 介绍
   zealsay是一套前后端分离的快速开发脚手架，后台采用的是最新的`Spring Boot 2.1.6.RELEASE`最新版本，
   前端使用vue,搭载比较火热的`nuxt.js`服务器端渲染框架，截止到目前,使用的是`nuxt.js`最新`2.8.1`版本,
   使用`Vuetify`构造出符合 `Material Design` 规范的扁平化风格主题UI,你有对美的偏爱,我同样有一份对美的执着,
   面对日趋多样化的技术,抽取一些常用的解决方案,以快,轻为主,打造出一个开箱即用的轻应用脚手架,助力中小企业解决
   快速部署以及持续交付的`DevOps`,本项目为zealsay后台项目，前端项目地址为 [zealsay_front](https://github.com/GodLikeZeal/zealsay_front)
## 技术选型
### zealsay_backend（后台项目）
- Framework : java 8
- Maven 3.5.4
- Lombok 1.18.8
- Spring Boot 2.1.6
- Spring Security
- Spring Oauth2 (整合第三方登录)
- jwt (JSON Web Token)
- jasypt 加密
- mapstruct (对象映射)
- Mybatis + Mybatis Plus (持久层开发利器)
- Spring Boot Docker (容器服务)
- Travis CI (自动化构建)
- Mysql (mysql数据库)
- Swagger 以及swagger bootstrap-ui (api文档在线生成)
- RabbitMQ (消息中间件)
- Redis (Nosql内存数据库)
- Hikari (高性能连接池)
- Feign (外部服务调用，整合spring cloud后可以用于内部服务调用)
- Logback (日志记录)
- Actuator (服务监控)
- Junit Test (单元测试)
- 第三方SDK或服务
  - 七牛云对象云存储
  - Github第三方登录
  - 微信第三方登录
  - QQ第三方登录
  - Hitokoto 一言接口
  - 阿里云短信服务
  

## 系统架构

这里是架构图

## 本地如何运行
本地运行可以有两种方式,可以使用传统的运行spring boot应用程序 java -jar 形式,
如果你本地安装了docker环境的话，也可以使用docker run的方式,
当然如果你安装了docker-compose,使用编排的方式更简洁直观,用哪种方式运行取决于你的个人喜好。

### 使用传统方式
#### 运行依赖
1.  确保本地安装jdk 1.8版本或以上。
2.  安装maven环境。
3.  安装git环境。
4.  安装mysql数据库(如果你有远程的数据库可以不用本地安装)
5.  安装redis(非必须,如果你不想使用,可以在bootstrap.yml里将redis配置这块去掉)
6.  安装RabbitMQ(非必须,同理,如果你不想使用,可以在bootstrap.yml里将rabbitmq配置这块去掉)
#### 代码检出
`git clone https://github.com/GodLikeZeal/zealsay_backend.git`
将代码clone到你的本地。
#### 配置
-  你可以不使用jasypt加密,直接在`bootstrap.yml`里面配置相关的连接信息,
当然,如果是一个企业级的项目的话,建议敏感信息还是使用加密保护比较好。
-  jasypt加密
1.  可以参考jasypt官方文档,设置必要的相关参数
2.  比如为了演示，我建立了一个 `Test`
```java
  @Test
  public void testEncrypt() throws Exception {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword("password"); //password
    config.setAlgorithm("PBEWithMD5AndDES"); //加密盐
    config.setKeyObtentionIterations("1000");
    config.setPoolSize("1");
    config.setProviderName("SunJCE"); //providerName
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); //设置SaltGeneratorClassName
    config.setStringOutputType("base64"); //输出格式
    encryptor.setConfig(config);
    String plainText = "test"; //需要加密的字符
    String encryptedText = encryptor.encrypt(plainText);
    System.out.println(encryptedText);
    Assert.assertNotNull(encryptedText);
  }
```
3.  由于使用了 `jasypt` 对敏感信息加密,
因此可以看到在`bootstrap.yml`中的配置数据库链接部分都是被加密过的,
你可以选择一个加密的`password`,比如我选择使用`key`作为加密的`password`,
即`config.setPassword("key");`,假如你要加密的是你数据库的`HOST`域名 `www.xxxx.com`,
那么可以 `String plainText = "www.xxxx.com";`。
4.  运行启动一下`Test`可以发现在日志打印一串加密后的字符,比如我的执行后得到 `AnoQmUYGtHaw3J/Tdp9SVb9Gr0Gcl69bsCY0cDlqHUGD0mxt9FlGjvEXCB5qfqyDbNulQaZoROw=`
然后这个值，你就可以在你`bootstrap.yml`配置数据库`HOST`的地方输入了,注意，一定要加上` ENC(你的加密值)`.

#### 启动中间件和数据库
- 启动mysql数据库,并且以上文所属的方法配置加密后的连接地址以及用户名和密码。
- 启动redis数据库(如果配置了)
- 启动RabbitMQ(如果配置了)

#### 启动项目
-  配置启动参数`jasypt.encryptor.password=你的password`
-  debug或run 你的`application`

### zealsay blog
基于zealsay快应用框架构建的博客系统,包含一个blog前端和对应的后台管理系统,为简约,美观和高性能而生。已实现部分功能,项目目前仍在开发中。
#### 在线预览
[项目在线预览地址](https://beta.zealsay.com)
#### 页面展示
博客首页
![image.png](https://pan.zealsay.com/20190716214935236000000.png)
首页文章列表
![image.png](https://pan.zealsay.com/20190716214936313000000.png)
博客详情页
![image.png](https://pan.zealsay.com/20190716214936530000000.png)
后台登录页面
![image.png](https://pan.zealsay.com/20190716214936631000000.png)
dashboard
![image.png](https://pan.zealsay.com/20190716214937367000000.png)
用户列表
![image.png](https://pan.zealsay.com/20190716214937929000000.png)
文章添加
![image.png](https://pan.zealsay.com/20190716214938738000000.png)
markdown编辑器
![image.png](https://pan.zealsay.com/20190716214939591000000.png)
标签云管理
![image.png](https://pan.zealsay.com/2019071621494080000000.png)
主题设置
![image.png](https://pan.zealsay.com/20190716214940767000000.png)
#### TODO LIST
*  登录模块
* [x] 用户名密码登录 
* [ ] Github第三方登录 
* [ ] 微信第三方登录 
* [ ] QQ第三方登录 
* [x] 登录背景动态气泡效果
*  用户模块
* [x] 后台用户列表条件搜索
* [x] 后台用户添加,删除
* [x] 后台用户修改
* [x] 后台用户详情
* [x] 后台用户封禁解封
* [ ] 个人中心(blog端和后台)
* [ ] 用户等级level以及称号
*  角色模块
* [x] 角色列表
* [x] 角色添加,删除
* [x] 角色修改
* [x] 角色详情
*  文章模块
* [x] 文章列表条件搜索
* [x] 文章添加,删除
* [x] 文章修改
* [x] 文章预览
* [x] 文章上下架
* [x] blog端首页文章列表
* [x] blog端根据类型搜索文章列表
* [x] blog端根据标签label搜索文章列表
* 标签云模块
* [x] 标签列表条件搜索
* [x] 标签添加,删除
* [x] blog端标签云展示
* [ ] blog端标签云动态气泡效果
* 分类目录模块
* [x] 分类目录树
* [x] 分类目录添加,删除
* [x] 分类目录修改
* 其他模块
* [x] 图片上传到服务器
* [x] 图片裁剪
* [x] sweetalert2弹窗样式自定义
* [ ] 解决sweetalert2弹窗按钮颜色不能马上生效
* [x] 增加markdown编辑器
* [x] 首页鼠标hover图片变大动画效果
* [ ] 博客端文章增加点赞功能
* [ ] 博客端文章增加浏览量功能
* [ ] 博客端关于页面，个人信息展示
* [ ] 博客端评论系统
* [ ] 用户自定义站点资源，如站点名称,meta,以及seo优化等
* [ ] 博客端友链页面card展示
* [ ] 后台dashboard流量，访问统计以及最新动态
* [ ] 页脚优化
* [ ] 文章浏览量展示
* [ ] 文章点赞/喜欢功能
* [ ] 文章分享转载功能
* [ ] 站点资源配置，如站点名称，meta以及seo等
* [ ] 博客端标签云气泡展示
### 反馈
欢迎大家在使用的过程中提出宝贵的意见和反馈问题，也可以直接提issue。
### 交流群
* 欢迎加入zealsay交流群一起交流和学习

<img src="https://pan.zealsay.com/20190716214941558000000.jpg" alt="Sample"  width="150" height="200">

### 鸣谢

- :kissing_heart::kissing_heart:感谢前端大佬[jinjinyike](https://github.com/jinjinyike)的顾问级别的帮助。
- :heart::heart:最后感谢兜兜里有糖的理解和支持,没有她我完不成此项目,别问为什么,晚上不睡觉写代码会被打。
- 更多关于nuxt文档,可以访问 [Nuxt.js docs](https://nuxtjs.org).
