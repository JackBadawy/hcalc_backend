FROM openjdk:17-jdk-slim
RUN groupadd -r mainuser && useradd -r -g mainuser mainuser
RUN mkdir /app && chown mainuser:mainuser /app

WORKDIR /app

VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

RUN chown mainuser:mainuser /app/app.jar

USER mainuser

ENTRYPOINT ["java","-jar","/app/app.jar"]