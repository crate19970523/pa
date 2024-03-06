FROM openjdk:21
COPY ./*.jar /usr/src/app/pa.jar
VOLUME /opt/pa
ENTRYPOINT ["java","-jar","/usr/src/app/pa.jar","--spring.config.location=file:/opt/pa/conf/application-pa.yml"]