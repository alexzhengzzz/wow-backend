FROM openjdk:11
MAINTAINER alexzhengzzz
COPY target/wow-backend-0.0.3-SNAPSHOT.jar wow-backend-0.0.3-SNAPSHOT.jar
CMD ["java", "-jar", "wow-backend-0.0.3-SNAPSHOT.jar", "--spring.profiles.active=docker"]