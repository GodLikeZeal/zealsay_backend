FROM maven:3.5.4-alpine
VOLUME /tmp
ARG key
ARG mode
ARG java_file
RUN mvn clean package -Djasypt.encryptor.password=${key}
COPY ${java_file} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Djasypt.encryptor.password=${key}","-Dspring.profiles.active=${mode}","-jar","app.jar"]
EXPOSE 8090