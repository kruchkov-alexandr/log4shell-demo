# Build stage
FROM maven:3.8-openjdk-8 AS builder
WORKDIR /app
COPY pom.xml .
COPY src/main/java/BadCode.java src/main/java/
COPY src/main/java/de/predic8/HttpServer.java src/main/java/de/predic8/
COPY src/main/java/de/predic8/LdapServer.java src/main/java/de/predic8/
RUN mvn compile

FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=builder /app/target/classes /app/target/classes
COPY --from=builder /root/.m2/repository /root/.m2/repository
COPY servers.sh .
RUN chmod +x servers.sh
EXPOSE 8080 10389
CMD ["./servers.sh"]
