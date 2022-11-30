# Web Crawler

## Information

- After the program is executed, the .jsonl with results will be located in `test-task/results`
- The file `.env` contains the following parametrs:
    - ROOT_URL - url where the search starts
    - TIMEOUT - the time(in seconds) after which the program will stop its execution
    - RESULT_FILE_NAME - name of .jsonl file with results

## Launching via the console

- ### If Maven and Java are installed 
    (I used maven v3.8.6 and openjdk-18)


 - `mvn package && java -cp target/test-task-1.0-SNAPSHOT.jar Main`

## Launch via the docker-compose

- `docker-compose up --build`
  

delete containers after launch:
  
- `docker rm $(docker ps -a -f name=test-task-crawler-* -q)
    `
