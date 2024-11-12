FROM openjdk:17-oracle
VOLUME /tmp
ADD health-1.0.0.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]