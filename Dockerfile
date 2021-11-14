FROM openjdk:11

COPY target/casestudy-0.0.1-SNAPSHOT.jar case-study-0.0.1.jar

ENTRYPOINT ["java", "-jar", "case-study-0.0.1.jar"]