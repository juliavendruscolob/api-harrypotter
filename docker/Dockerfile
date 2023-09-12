FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY ${JAR_FILE} spring-boot-api-harrypotter.jar
ENTRYPOINT ["java", "-jar", "/spring-boot-api-harrypotter.jar"]