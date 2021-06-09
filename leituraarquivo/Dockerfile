FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
ADD /target/leituraarquivo.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]