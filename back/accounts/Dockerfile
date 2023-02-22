FROM openjdk:17

VOLUME /tmp

EXPOSE 8000

ARG JAR_FILE=build/libs/accounts-1.0.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]