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

# Set ownership of the jar
RUN chown mainuser:mainuser /app/app.jar

# Switch to non-root user
USER mainuser

# Use port 8080 as specified in your application
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","/app/app.jar"]