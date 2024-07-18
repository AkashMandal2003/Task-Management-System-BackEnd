FROM openjdk:21
COPY target/taskmanagementsystem-0.0.1-SNAPSHOT.jar taskmanagementsystem-0.0.1-SNAPSHOT.jar
#EXPOSE 8080

ENTRYPOINT ["java", "-jar", "taskmanagementsystem-0.0.1-SNAPSHOT.jar"]