FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/attendance-0.0.1-SNAPSHOT.jar app.jar
RUN mkdir -p /app/data
EXPOSE 8081
ENV PORT=8081
ENTRYPOINT ["java","-jar","app.jar"]
