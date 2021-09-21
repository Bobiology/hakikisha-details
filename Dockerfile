FROM openjdk:11
ADD target/hakikisha-info-0.0.1-SNAPSHOT.jar hakikisha-info-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","hakikisha-info-0.0.1-SNAPSHOT.jar"]