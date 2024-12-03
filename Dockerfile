FROM openjdk:17-jdk-slim

# Install curl for healthcheck
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Create non-root user
RUN groupadd -r mainuser && useradd -r -g mainuser mainuser
RUN mkdir /app && chown mainuser:mainuser /app

WORKDIR /app

# Copy the jar and keystore files
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/keystore.p12 /app/keystore.p12

# Set ownership of the files
RUN chown mainuser:mainuser /app/app.jar /app/keystore.p12

# Switch to non-root user
USER mainuser

# Add enhanced debug logging
ENV JAVA_TOOL_OPTIONS="\
-Dlogging.level.root=DEBUG \
-Dlogging.level.org.springframework=DEBUG \
-Dlogging.level.com.jack=DEBUG \
-Dlogging.level.org.hibernate=DEBUG \
-Dserver.address=0.0.0.0 \
-Dserver.port=8080 \
-Dlogback.statusListenerClass=ch.qos.logback.core.status.OnConsoleStatusListener"

# Use port 8080
EXPOSE 8080

# Add healthcheck (using localhost since we're checking from inside the container)
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl --fail -k https://0.0.0.0:8080/api/auth/health || exit 1

# Run with verbose options
ENTRYPOINT ["java", "-jar", \
"-XX:+PrintFlagsFinal", \
"-Dspring.profiles.active=prod", \
"-Djava.security.egd=file:/dev/./urandom", \
"-Dserver.address=0.0.0.0", \
"-Dserver.port=8080", \
"/app/app.jar"]