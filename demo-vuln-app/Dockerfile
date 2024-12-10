FROM maven:3.8-openjdk-8 as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package

FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=builder /app/target/log4j-demo-1.0-SNAPSHOT.jar ./app.jar
ENV JAVA_OPTS="-Dcom.sun.jndi.ldap.object.trustURLCodebase=true"
EXPOSE 9090
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
