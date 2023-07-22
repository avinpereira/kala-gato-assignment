# Use a base image with Java installed
FROM adoptopenjdk:11-jre-hotspot

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file to the container
COPY target/TollApplication-0.0.1-SNAPSHOT.jar TollApplication-0.0.1-SNAPSHOT.jar

# Expose the port that your Spring Boot application listens on
EXPOSE 8080

# Set the entry point for the container (command to run your Spring Boot app)
ENTRYPOINT ["java", "-jar", "TollApplication-0.0.1-SNAPSHOT.jar"]