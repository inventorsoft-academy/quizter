FROM openjdk:13
ADD /target/quizter.jar quizter.jar
CMD ["java", "-jar", "quizter.jar"]
