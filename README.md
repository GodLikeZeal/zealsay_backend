<p align="center"><a href="https://vuejs.org" target="_blank" rel="noopener noreferrer"><img width="100" src="https://pan.zealsay.com/2019091615686216710547.png" alt="Vue logo"></a></p>


<p align="center">

 ![java 8](https://img.shields.io/badge/java-8-ff69b4.svg)
![spring boot 2.1.6](https://img.shields.io/badge/springboot-2.1.6-green.svg) ![swagger valid ](https://img.shields.io/badge/swagger-valid-brightgreen.svg) ![License MPL-2.0](https://img.shields.io/badge/license-MPL--2.0-green.svg)  [![Build Status](https://travis-ci.org/GodLikeZeal/zealsay_backend.svg?branch=master)](https://travis-ci.org/GodLikeZeal/zealsay_backend) [![](https://img.shields.io/docker/stars/zealsay/zealsay_backend.svg)](https://hub.docker.com/r/zealsay/zealsay_backend 'DockerHub') [![](https://img.shields.io/docker/pulls/zealsay/zealsay_backend.svg)](https://hub.docker.com/r/zealsay/zealsay_backend 'DockerHub') ![GitHub All Releases](https://img.shields.io/github/downloads/GodLikeZeal/zealsay_backend/total)

</p>

------
## 开发指南
请参考 [zealsay开发文档](https://docs.zealsay.com)
本项目代码地址[https://github.com/GodLikeZeal/zealsay_backend](https://github.com/GodLikeZeal/zealsay_backend)，国内版[https://gitee.com/GodLikeZeal/zealsay_backend](https://gitee.com/GodLikeZeal/zealsay_backend)，喜欢的话，欢迎star。
## 介绍
   zealsay是一套前后端分离的快速开发脚手架，后台采用的是最新的`Spring Boot 2.1.6.RELEASE`最新版本，
   前端使用vue,搭载比较火热的`nuxt.js`服务器端渲染框架，截止到目前,使用的是`nuxt.js`最新`2.12.2`版本,
   使用`Vuetify 2.x`构造出符合 `Material Design` 规范的扁平化风格主题UI,你有对美的偏爱,我同样有一份对美的执着,
   面对日趋多样化的技术,抽取一些常用的解决方案,以快,轻为主,打造出一个开箱即用的轻应用脚手架,助力中小企业解决快速部署以及持续交付的`DevOps`。
   > 本项目以个人博客网站为示例，展示如何使用`zealsay`快速搭建一个漂亮的个人博客，前后端项目全部开源，此项目为zealsay后台项目，前端项目地址为:[https://github.com/GodLikeZeal/zealsay_front](https://github.com/GodLikeZeal/zealsay_front)，国内[https://gitee.com/GodLikeZeal/zealsay_front](https://gitee.com/GodLikeZeal/zealsay_front)，欢迎frok，发现bug或者有好的建议也欢迎issue。
- 后台管理登录[https://beta.zealsay.com](https://beta.zealsay.com) 体验账号用户名:visitor 密码：abc123
- api接口文档[https://dev-api.zealsay.com/doc.html](https://dev-api.zealsay.com/doc.html) 授权用户名：zealsay 密码:api123456
#### 以本项目开发的博客系统已成功上线部署，欢迎体验[https://blog.zealsay.com](https://blog.zealsay.com)
 
-------
本项目会一直持续更新迭代，新功能正在马不停蹄更新，开发文档也正在不断的完善中...
## 技术选型
### zealsay_backend（后台项目）
- Framework : java 8
- Maven 3.5.4
- Lombok 1.18.8
- Spring Boot 2.1.6
- Spring Security
- Spring Oauth2 (整合第三方登录)
- Spring Validation(参数校验优雅自如)
- jwt (JSON Web Token)
- jasypt 加密
- mapstruct (对象映射)
- Mybatis + Mybatis Plus (持久层开发利器)
- Fastjson (阿里json工具包)
- Spring Boot Docker (容器服务)
- Travis CI (自动化构建)
- Mysql (mysql数据库)
- Swagger 以及swagger bootstrap-ui (api文档在线生成)
- <s>RabbitMQ (消息中间件，个人博客示例中，为节省资源考虑去掉)</s>
- Redis (Nosql内存数据库)
- Hikari (高性能连接池)
- Undertow(高性能服务器容器，告别tomcat和jetty)
- Feign (外部服务调用，整合spring cloud后可以用于内部服务调用)
- Logback (日志记录)
- Actuator (服务监控)
- Junit Test (单元测试)
- 第三方SDK或服务
  - 七牛云对象云存储
  - Github第三方登录
  - Gitee第三方登录
  - Hitokoto 一言接口
  - 阿里云短信服务
  - 图灵机器人
  - 邮箱Email服务
  

## 系统架构

![系统架构图](https://pan.zealsay.com/mweb/2020061915925585465233.png)


## 快速搭建一个博客
 有两种方式可以来搭建，分别为传统方式和docker容器方式
 
-------

### 1.docker容器方式部署
- 先决条件：
1. 你得有一台安装了docker的主机或者服务器。
2. 你得安装docker-compose容器编排利器
3. 确保你的服务器安全组开放了mysql,redis还有服务app的端口号访问

-------
好了废话不多说，直接上`docker-compose.yml`编排文件

```yml
version: "3.3"
services:
  zealsay_service:
    image: registry.cn-qingdao.aliyuncs.com/zealsay/zealsay_backend:latest  #国产私服更快，docker hub有时候不稳定
    container_name: zealsay_service
    expose:
      - 8090 #默认为8090
    environment:
      - VIRTUAL_HOST=xxx.xxx.xxx #改成你后台服务的域名,如 www.baidu.com
      - REDIS_HOST=redis #改成你redis的ip，此处为docker内部引用
      - REDIS_PORT=6379 #改成你redis的端口
      - REDIS_PASSWORD=your redis password #改成你redis的认证密码
      - MYSQL_HOST=mysql #改成你mysql的ip，此处为docker内部引用
      - MYSQL_USERNAME=username #改成你mysql的用户名
      - MYSQL_PASSWORD=password #改成你mysql的认证密码
      - MAIL_USERNAME=xxx@xxx.com #改成你发送系统邮件的邮箱
      - MAIL_PASSWORD=email password #改成你邮箱的密码
      - QINIU_DOMAIN=https://xxx.xxx.xxx/ #改成你七牛云域名,上传图片和文件用
      - QINIU_BUCKET=your bucket #改成你七牛云bucket
      - QINIU_ACCESSKEY=accesskey #改成你的七牛accesskey
      - QINIU_SECRETKEY=secretkey #改成你的secretkey
      - GITHUB_ID=123 #改成你的github授权client-id
      - GITHUB_SECRET=123 #改成你的github授权client-secret
      - GITHUB_URI=http://xxx.xxx.xxx/call/back #改成你的github授权redirect-uri
      - WEB_NAME=zealsay说你想说 #改成你的blog站点名称
      - WEB_DOMAIN=http://xxx.xxx.xxx/ #改成你的domain
      - API_USERNAME=username #swagger api访问用户名
      - API_PASSWORD=password #swagger api访问密码
    external_links: 
      - mysql
      - redis
  zealsay_web:
    image: registry.cn-qingdao.aliyuncs.com/zealsay/zealsay_front:latest
    container_name: zealsay_web
    expose:
      - 4000
    depends_on:
      - zealsay_service
    external_links: 
      - nginx-proxy-zealsay
    environment:
      - VIRTUAL_HOST=xxx.xxx.xxx #改成你的站点的域名,如www.baidu.com
      - VUE_APP_API_URL=https://xxx.xxx.xxx #改成你后台服务域名，跟上面的VIRTUAL_HOST保持一致
  redis:
    image: redis:5-alpine
    container_name: redis
    command: redis-server --requirepass dev_redis
    networks:
      - default
    ports:
      - "6379:6379"
    volumes:
      - redis_conf:/usr/local/etc/redis
      - redis_data:/data
  mysql:
    image: mysql:5.7
    container_name: mysql
    ports:
      - "3306:3306"
      - "33060:33060"
    command: [
            '--character-set-server=utf8mb4',
            '--collation-server=utf8mb4_bin',
            '--default-time-zone=+8:00'
    ]
    networks:
      - default
    volumes:
      - mysql_data:/var/lib/mysql
      - mysql_conf:/etc/mysql/conf.d
    environment:
      - MYSQL_ROOT_PASSWORD=password #你数据库root用户的密码
      - MYSQL_DATABASE=zealsay #你zealsay项目的数据库
      - MYSQL_USER=user #创建一个mysql用户
      - MYSQL_PASSWORD=user password #你创建用户的密码

       # 以下是nginx反向代理服务的配置
  nginx-proxy:
    image: jwilder/nginx-proxy
    container_name: nginx-proxy-zealsay
    restart: always
    ports:
      - "80:80"
      - "443:443" # ssl 默认是443端口，因此需要将443端口映射到宿主机上
    volumes:
      - /var/run/docker.sock:/tmp/docker.sock:ro # 将宿主机的docker.sock绑定到nginx，这样，今后添加新的站点时，nginx将会自动发现站点并重启服务
      - certs:/etc/nginx/certs:ro # 将nginx中的证书目录，映射到宿主机中



# 配置一个公共外部网络来联通所有容器
networks:
  default:
    external:
      name: zealsay

volumes:
  redis_data:
  redis_conf:
  mysql_data:
  mysql_conf:
  certs: 

```
### 2.传统java部署
#### 运行依赖
1. 确保本地安装jdk 1.8版本或以上。
2. 安装maven环境。
3. 安装git环境。
4. 安装mysql数据库(如果你有远程的数据库可以不用本地安装)
5. 安装redis
#### 代码检出
`git clone https://github.com/GodLikeZeal/zealsay_backend.git`
将代码clone到你的本地。
#### 修改配置文件
修改`application.yml`

```yml
---
spring:
  profiles: prod
  redis:
    host: ${REDIS_HOST} #你的redis ip
    port: ${REDIS_PORT} #你的redis端口
    password: ${REDIS_PASSWORD} #你的redis密码
    timeout: 10000
  datasource:
    host: ${MYSQL_HOST} #你的mysql ip
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: ${MYSQL_USERNAME} #你的mysql 用户名
    password: ${MYSQL_PASSWORD} #你的mysql 密码
    url: jdbc:mysql://${spring.datasource.host}/zealsay?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false
    hikari:
      maxLifetime: 1765000 #一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒以上
      maximumPoolSize: 15 #连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
  servlet:
    multipart:
      max-file-size: 5MB
      max-request-size: 50MB
  mail:
    host: smtp.mxhichina.com
    username: ${MAIL_USERNAME} #你的email用户名
    password: ${MAIL_PASSWORD} #你的email密码
    default-encoding: UTF-8
swagger:
  basic:
    ## 开启Swagger的Basic认证功能,默认是false
    enable: true
    ## Basic认证用户名
    username: ${API_USERNAME} #api接口授权用户
    ## Basic认证密码
    password: ${API_PASSWORD} #api接口授权用户
qiniu:
  Domain: ${QINIU_DOMAIN} #你的七牛云域名
  Bucket: ${QINIU_BUCKET} #你的七牛云backet
  AccessKey: ${QINIU_ACCESSKEY} #你的七牛云AccessKey
  SecretKey: ${QINIU_SECRETKEY} #你的七牛云SecretKey

web:
  name: zealsay说你想说 #你的web名称
  domain: https://blog.zealsay.com/ #你的domian
  default:
    password: $2a$10$DmMktUppa9g.qWPmGPOM/.VQGP0Njrf09vC3FIbNp2G91R1Xdq466 #后台管理添加用户的默认密码，为加密后的密码

justauth:
  enabled: true
  type:
    github:
      client-id: 123 #你的github client-id
      client-secret: 123 #你的github client-secret
      redirect-uri: https://xxx.xxx.xxx/api/v1/oauth/github/callback #你的github授权回调
  cache:
    type: default
```
#### package
执行`mvn clean package`打包项目
#### 运行项目
进入target目录，执行jar文件
`java -Dspring.profiles.active=prod -jar zealsay-1.0.0.jar`

### 谁在使用
zealsay博客[https://blog.zealsay.com](https://blog.zealsay.com)
### 反馈
欢迎大家在使用的过程中提出宝贵的意见和反馈问题，也可以直接提issue。
### 交流群
* 欢迎加入zealsay交流群一起交流和学习，群号：189361484

<img src="https://pan.zealsay.com/20190716214941558000000.jpg" alt="Sample"  width="150" height="200">

### 鸣谢
- 感谢开发神器[Mybatis-Plus](https://mp.baomidou.com/)
- 感谢dto转换工具[Mapstruct](https://github.com/mapstruct/mapstruct)
- 感谢友好的swagger ui插件[knife4j](https://gitee.com/xiaoym/knife4j)
- :kissing_heart::kissing_heart:感谢前端妹子[jinjinyike](https://github.com/jinjinyike)的帮助。
- :heart::heart:兜兜里有糖。
- 作为一位后台开发者,接触前端时间不长，熟悉了vue后，便使用nuxt.js来开发此项目,刚使用nuxt,踩了不少坑,更多关于nuxt文档,可以访问 [Nuxt.js docs](https://nuxtjs.org).
