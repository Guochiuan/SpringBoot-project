FROM openjdk:11
ADD target/DroneDelivery-0.0.1-SNAPSHOT.jar DroneDelivery-docker.jar
EXPOSE 8080
LABEL maintainer = "gatech.edu"
ENTRYPOINT ["java", "-jar", "DroneDelivery-docker.jar"]