FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
COPY target/shareit-server-0.0.1-SNAPSHOT.jar shareit-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/shareit-server-0.0.1-SNAPSHOT.jar"]
