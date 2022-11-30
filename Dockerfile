FROM maven:3.8.6-openjdk-18
WORKDIR /test-task
COPY . .
RUN mvn package
RUN java -cp target/test-task-1.0-SNAPSHOT.jar Main
