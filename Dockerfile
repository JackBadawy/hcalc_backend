FROM openjdk:17-jdk-slim

# Create non-root user
RUN groupadd -r mainuser && useradd -r -g mainuser mainuser
RUN mkdir /app && chown mainuser:mainuser /app

WORKDIR /app

# Create volume for temporary files
VOLUME /tmp

# Copy the jar file
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/keystore.p12 /app/keystore.p12

# Set ownership of the jar
RUN chown mainuser:mainuser /app/app.jar /app/keystore.p12

# Switch to non-root user
USER mainuser

# Add debug logging
ENV JAVA_TOOL_OPTIONS="-Dlogging.level.org.springframework=DEBUG -Dlogging.level.com.jack=DEBUG"

# Use port 8080 as specified in your application
EXPOSE 8080

# Add healthcheck for HTTPS
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl --fail https://localhost:8080/api/auth/health --insecure || exit 1

# Run the application with debug options
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app/app.jar"]