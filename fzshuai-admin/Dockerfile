FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER fzshuai-156117106@qq.com

RUN mkdir -p /fzshuai/server/logs \
    /fzshuai/server/temp \
    /fzshuai/skywalking/agent

ENV TZ="Asia/Shanghai"

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone

WORKDIR /fzshuai/server

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

ADD ./target/fzshuai-admin.jar ./app.jar

ENTRYPOINT ["java", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-Dserver.port=${SERVER_PORT}", \
            # 应用名称 如果想区分集群节点监控 改成不同的名称即可
#            "-Dskywalking.agent.service_name=fzshuai-server", \
#            "-javaagent:/fzshuai/skywalking/agent/skywalking-agent.jar", \
            "-jar", "app.jar"]
