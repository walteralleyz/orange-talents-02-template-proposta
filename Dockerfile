FROM openjdk:11
ARG JAR_FILE=target/*.jar
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
ENV STATUS_API=http://0.0.0.0:9999
ENV CARD_API=http://0.0.0.0:8888
ENTRYPOINT java -jar /app.jar
