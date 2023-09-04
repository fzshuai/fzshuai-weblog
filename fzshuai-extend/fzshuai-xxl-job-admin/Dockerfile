FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER fzshuai

RUN mkdir -p /fzshuai/xxljob/logs

WORKDIR /fzshuai/xxljob

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 9100

ADD ./target/fzshuai-xxl-job-admin.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
