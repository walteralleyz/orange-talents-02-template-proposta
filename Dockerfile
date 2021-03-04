FROM openjdk:11
ARG JAR_FILE=target/*.jar
EXPOSE 8080
COPY ${JAR_FILE} app.jar
ENV DS_DRIVERCLASS=org.h2.Driver
ENV DS_URL=jdbc:h2:mem:test
ENV DS_USER=sa
ENV DS_PASS=
ENV JPA_DIALECT=org.hibernate.dialect.H2Dialect
ENV JPA_DDL=update
ENV JPA_SHOWSQL=true
ENV JPA_FORMATSQL=true
ENV JPA_VIEW=false
ENV STATUS_API=http://172.23.0.1:9999
ENV CARD_API=http://172.23.0.1:8888
ENV KEYCLOAK_REALM=usuarios
ENV KEYCLOAK_API=http://172.23.0.1:18080
ENV KEYCLOAK_CLIENT=credicard-client
ENV KEYCLOAK_SECRET=c708a4b2-6f4f-44f8-a119-b7e15e991e17
ENTRYPOINT java -Dspring.profiles.active=container -jar /app.jar
