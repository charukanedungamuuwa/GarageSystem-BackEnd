# Use the official OpenJDK image as a parent image
FROM openjdk:21

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/AuthService-1.0-SNAPSHOT.jar app.jar

# Run the JAR file
CMD ["java", "-jar", "app.jar"]