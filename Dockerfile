FROM maven:3.8.1-jdk-11 as build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /build/src/
RUN mvn package

FROM openjdk:11-jdk-slim
COPY --from=build /build/target/ /app/
WORKDIR /app
RUN cp ejemplo_javalin-1.0-SNAPSHOT.jar dependency
EXPOSE 7000

CMD /bin/sh -c 'java -cp "/app/dependency/*" ar.edu.utn.dds.libros.AppLibros'
