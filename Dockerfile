FROM eclipse-temurin
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 3000
ENTRYPOINT ["java","-jar","/app.jar"]

# ./gradlew build && java -jar build/libs/RESTServerApp.jar
# docker build --build-arg JAR_FILE=build/libs/\*.jar -t arirokk/restserverapp .
# docker run -d -p 3000:3000 arirokk/restserverapp