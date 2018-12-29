#  zealsay 
------
 ![beta 1.0.0](https://img.shields.io/badge/beta-1.0.0-ff69b4.svg)
![spring boot 2.0.4](https://img.shields.io/badge/spring%20boot-2.0.4-green.svg) ![swagger valid ](https://img.shields.io/badge/swagger-valid-brightgreen.svg) ![License MPL-2.0](https://img.shields.io/badge/license-MPL--2.0-green.svg)  [![Build Status](https://travis-ci.org/GodLikeZeal/zealsay_backend.svg?branch=master)](https://travis-ci.org/GodLikeZeal/zealsay_backend) [![](https://img.shields.io/docker/stars/zealsay/zealsay_backend.svg)](https://hub.docker.com/r/zealsay/zealsay_backend 'DockerHub') [![](https://img.shields.io/docker/pulls/zealsay/zealsay_backend.svg)](https://hub.docker.com/r/zealsay/zealsay_backend 'DockerHub')

## 项目介绍

该项目是zealsay blog的后台服务项目,包含B端后台管理和C端展示逻辑，
目前市面上的大多数博客都是采用基于php的wordpress或者是基于java的hexo搭建，在追求快速搭建个人博客的过程中，
一直很难在精简优雅和功能丰富间平衡，因为这本身就是一个矛盾的过程，因此本项目只是致力于构建集精美UI,
强大稳定后台与一体的个人博客快速搭建解决方案。
主要用到的技术栈如下：

- framework : java 8
- maven
- lombok
- spring boot 2.x
- spring security
- jwt
- jasypt 加密
- mapstruct (对象映射)
- mybatis + mybatis plus
- spring-boot docker
- travis ci
- mysql
- swagger 以及swagger bootstrap-ui

## 起步

1.  将项目clone到你本地。

    `git clone https://github.com/GodLikeZeal/zealsay_backend.git`
    
2.  修改配置文件,因为项目的application.yml中的一些关键信息都已经使用jasypt加密了，将其中的加密信息修改成自己的信息就好了。
3.  执行 `mvn spring-boot:run `启动项目。

## 如何部署到远程服务器

## 如何贡献代码

首先将代码 `git clone`到本地之后，可以在自己本地提交代码，所有的改动最好写好
注释。如下：

[feat] 修改用户登录接口 related to issue #45

其中

*  `feat`：新功能（feature）
*  `fix`：修补bug
*  `docs`：文档（documentation）
*  `style`： 格式（不影响代码运行的变动）
*  `refactor`：重构（即不是新增功能，也不是修改bug的代码变动）
*  `test`：增加测试
*  `chore`：构建过程或辅助工具的变动

committer邮件地址必须是登录的邮箱.

## 开发说明

所有的模块都要写测试用例，因为`travis ci`钩子在自动构建的时候会先去跑测试用例，
通过了才去构建。

建议下载`idea`编辑器进行开发，并且推荐使用mybatis插件`MybatisX`进行开发，功能非常强大.

## 服务器后台启动

`nohup java -jar zealsay-1.0.0.jar >zealsay.log 2>&1 &`
