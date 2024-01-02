# Build Stage
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /
COPY . .
RUN mvn clean install -DskipTests


# Final Stage
FROM openjdk:17
WORKDIR /
COPY --from=builder /target/spring-boot-mysql-docker.jar .
CMD ["java", "-jar", "spring-boot-mysql-docker.jar"]