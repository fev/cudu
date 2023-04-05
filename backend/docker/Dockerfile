FROM openjdk:8-jdk-slim
LABEL maintainer="Martin Espinach <martin.espinach@jamgo.coop>"

COPY ./build/libs/cudu-2.3.0.jar /srv/app/app.jar

WORKDIR /srv/app

CMD ["java", "-XX:+UseG1GC",  "-Xmx2g", "-Dcom.sun.management.jmxremote.port=9999", "-Dcom.sun.management.jmxremote.authenticate=false", "-Dcom.sun.management.jmxremote.ssl=false", "-jar", "app.jar"]
