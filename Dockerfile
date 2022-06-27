FROM maven:3.8.6-jdk-8 as build
COPY . /app
WORKDIR /app
RUN mvn package

FROM openjdk:8-jdk-alpine
COPY --from=build /app/target/ /app/
EXPOSE 7000
CMD mvn exec:java -Dexec.mainClass="ar.edu.utn.dds.libros"'