#  zealsay 
------
 ![beta 1.0.0](https://img.shields.io/badge/beta-1.0.0-ff69b4.svg)
![spring boot 2.0.4](https://img.shields.io/badge/spring%20boot-2.0.4-green.svg) ![swagger valid ](https://img.shields.io/badge/swagger-valid-brightgreen.svg) ![License MPL-2.0](https://img.shields.io/badge/license-MPL--2.0-green.svg)  [![Build Status](https://travis-ci.org/GodLikeZeal/zealsay_backend.svg?branch=master)](https://travis-ci.org/GodLikeZeal/zealsay_backend) [![](https://img.shields.io/docker/stars/zealsay/zealsay_backend.svg)](https://hub.docker.com/r/zealsay/zealsay_backend 'DockerHub') [![](https://img.shields.io/docker/pulls/zealsay/zealsay_backend.svg)](https://hub.docker.com/r/zealsay/zealsay_backend 'DockerHub')

## 介绍
   zealsay是一套前后端分离的快速开发脚手架，后台采用的是最新的`Spring Boot 2.1.6.RELEASE`最新版本，
   前端使用vue,搭载比较火热的`nuxt.js`服务器端渲染框架，截止到目前,使用的是`nuxt.js`最新`2.8.1`版本,
   使用`Vuetify`构造出符合 `Material Design` 规范的扁平化风格主题UI,你有对美的偏爱,我同样有一份对美的执着,
   面对日趋多样化的技术,抽取一些常用的解决方案,以快,轻为主,打造出一个开箱即用的轻应用脚手架,助力中小企业解决
   快速部署以及持续交付的`DevOps`
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
  
### zealsay_front（前端项目）
- Vue (前端炙手可热的三大框架之一)
- Nuxt (vue服务端渲染框架)
- Vuetify (基于Material Design风格规范优美的主题)
- Vuex (全局状态管理)
- nuxtjs/auth (登录授权)
- nuxtjs/axios (axios请求)
- eslint (格式化)
- less (less样式语法)
- sass (sass样式语法)
- stylus (stylus样式语法)
- vue-chartist (表格插件)
- mdi/font (字体插件)
- sweetalert2 (优美的弹窗)
- vue-cropper (图片裁剪)
- vuetify-dialog (对话框)
- mavon-editor (md编辑器)
- travis ci (自动化ci)
- docker (容器服务)

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

