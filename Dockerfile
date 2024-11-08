FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /target/GAI_Docs-0.0.1-SNAPSHOT.jar /app/GAI_Docs.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "GAI_Docs.jar"]