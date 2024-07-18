FROM openjdk:21

# Copy the project files to the container
COPY . /app

# Set the working directory to /app
WORKDIR /app

# Run the Maven package command to build the project
RUN ./mvnw package

# Copy the built JAR file to the correct location
COPY target/taskmanagementsystem-0.0.1-SNAPSHOT.jar taskmanagementsystem-0.0.1-SNAPSHOT.jar

# Uncomment if you want to expose the port
#EXPOSE 8080

# Set the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "taskmanagementsystem-0.0.1-SNAPSHOT.jar"]
