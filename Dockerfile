FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

RUN groupadd -r mainuser && useradd -r -g mainuser mainuser
RUN mkdir /app && chown mainuser:mainuser /app

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

RUN chown mainuser:mainuser /app/app.jar

USER mainuser

ENV JAVA_TOOL_OPTIONS="\
-Dlogging.level.root=DEBUG \
-Dlogging.level.org.springframework=DEBUG \
-Dlogging.level.com.jack=DEBUG \
-Dlogging.level.org.hibernate=DEBUG \
-Dserver.address=0.0.0.0 \
-Dserver.port=8080 \
-Dlogback.statusListenerClass=ch.qos.logback.core.status.OnConsoleStatusListener"

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl --fail http://0.0.0.0:8080/api/auth/health || exit 1

ENTRYPOINT ["java", "-jar", \
"-XX:+PrintFlagsFinal", \
"-Dspring.profiles.active=prod", \
"-Djava.security.egd=file:/dev/./urandom", \
"-Dserver.address=0.0.0.0", \
"-Dserver.port=8080", \
"/app/app.jar"]