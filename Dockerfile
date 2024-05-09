FROM openjdk:21

COPY ./target/*.jar /usr/src/transactions/application.jar

WORKDIR /usr/src/transactions

CMD ["java", "-jar", "/usr/src/transactions/application.jar"]
