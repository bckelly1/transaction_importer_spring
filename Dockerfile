FROM maven:3.9.6
#FROM openjdk:21

COPY . /usr/src/transactions/
#COPY ./target/*.jar /usr/src/transactions/application.jar

WORKDIR /usr/src/transactions

RUN mvn clean install
RUN mv /usr/src/transactions/target/*.jar /usr/src/transactions/application.jar

CMD ["java", "-jar", "/usr/src/transactions/application.jar"]

