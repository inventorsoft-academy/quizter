1. In property file change property to:
`spring.datasource.url=jdbc:postgresql://postgres:5432/quizter`
2. Generate jar file
`mvn clean install`
3. Type in terminal
`docker build ./ -t quizter_app` to create app image
4. Type in terminal `docker-compose up` to start app
5. Press `ctrl + c` to stop execution