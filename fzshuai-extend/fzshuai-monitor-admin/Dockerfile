FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER fzshuai

RUN mkdir -p /fzshuai/monitor/logs

WORKDIR /fzshuai/monitor

EXPOSE 9090

ADD ./target/fzshuai-monitor-admin.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
