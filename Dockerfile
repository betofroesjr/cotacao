FROM openjdk:8-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/cotacaoBMF.jar
ADD init.sql /app
WORKDIR /app
ENTRYPOINT java -jar cotacaoBMF.jar