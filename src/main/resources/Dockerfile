FROM openjdk:8-jdk-slim
LABEL maintainer=csm
COPY target/*.jar  /menu.jar
ENTRYPOINT ["java","-jar","menu.jar"]