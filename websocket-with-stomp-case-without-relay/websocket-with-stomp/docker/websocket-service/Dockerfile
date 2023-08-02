FROM openjdk:20-jdk-buster
LABEL maintainer="nexttechuser"
VOLUME /tmp
WORKDIR /app

COPY *.jar ROOT.jar
COPY application*.yaml .
RUN sh -c 'touch /app/ROOT.jar' ROOT.jar
EXPOSE 8080
ENV ACTIVE_PROFILE=''

ENTRYPOINT ["sh", "-c", \
"java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8001,suspend=n \
-Dspring.config.additional-location=./ \
-Dspring.profiles.active=$ACTIVE_PROFILE \
-jar /app/ROOT.jar"]