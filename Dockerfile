FROM eclipse-temurin:17 as builder
WORKDIR /src
COPY . .
RUN ./gradlew clean bootJar

FROM eclipse-temurin:17
COPY --from=builder /src/build/libs/*.jar /app.jar
COPY --from=builder /src/src/main/resources/application.properties /resources/application.properties
ENTRYPOINT ["java", "-jar", "/app.jar"]
CMD ["--spring.config.location=/resources/application.properties"]
