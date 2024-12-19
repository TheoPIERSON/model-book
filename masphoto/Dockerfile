FROM maven:3-openjdk-17 as build

ENV MAVEN_HOME=/usr/local/maven
ENV PATH $PATH:/usr/local/maven/bin
WORKDIR /app/masphoto/

COPY ./pom.xml /app/masphoto/pom.xml
COPY ./ /app/masphoto/

RUN mvn clean package -DskipTests

FROM openjdk:17

VOLUME /tmp
COPY --from=build /app/masphoto/target/masphoto-0.0.1-SNAPSHOT.jar /app/masphoto/target/
EXPOSE 8081

CMD ["java", "-jar", "/app/masphoto/target/masphoto-0.0.1-SNAPSHOT.jar"]