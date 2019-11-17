FROM openjdk:8
ADD /target/quizter.jar quizter.jar
CMD ["java", "-jar", "quizter.jar"]