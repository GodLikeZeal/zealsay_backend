#!/usr/bin/env bash
export SPRING_PROFILES_ACTIVE=prod
export APP_PORT=8090
#...export others environment variables
cd /home/travis/zealsay/zealsay_backend
# update the repository
echo 开始更新git仓库...
git pull
echo 更新成功!
# remove the old container
echo 开始删除旧的镜像...
docker-compose -f docker-compose.yml rm -fs
echo 旧镜像删除成功!
# start the container
echo 服务启动中...
docker-compose -f docker-compose.yml up -d
echo 服务启动完毕!
cd /home/travis/zealsay/zealsay_backend
# update the api doc
echo 开始更新api文档!
#apidoc
