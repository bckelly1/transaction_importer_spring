FROM maven:3.9.6

COPY . /usr/src/transactions/

WORKDIR /usr/src/transactions

RUN mvn clean install
RUN mv /usr/src/transactions/target/*.jar /usr/src/transactions/application.jar

CMD ["java", "-jar", "/usr/src/transactions/application.jar"]

