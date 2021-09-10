FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml
RUN ./mvnw -Dmaven.repo.local=./.m2 dependency:go-offline
COPY src src
RUN ./mvnw -Dmaven.repo.local=./.m2 compile
EXPOSE 8080
CMD ["./mvnw", "-Dmaven.repo.local=./.m2", "jetty:run"]
