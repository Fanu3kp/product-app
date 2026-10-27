FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven && \
    mvn package -DskipTests && \
    apt-get remove -y maven && \
    apt-get autoremove -y

EXPOSE 8080

CMD ["java", "-jar", "target/backend-1.0.0.jar"]
