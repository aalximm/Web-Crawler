FROM maven:3.8.6-openjdk-18
WORKDIR /test-task
COPY . .
RUN mvn package
