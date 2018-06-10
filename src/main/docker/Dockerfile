FROM openjdk:8-jre-slim

MAINTAINER Deepak Kumar <deepak.kumar.iet@gmail.com>

ENV ENV_NAME local
ENV BOOTAPP_JAVA_OPTS -Xms256m -Xmx256m
ENV BOOTAPP_USR="bootapp" BOOTAPP_GROUP="app_group" BOOTAPP_PATH="/app.jar"
ENV SERVER_PORT 8000

EXPOSE $SERVER_PORT

COPY wrapper.sh /wrapper.sh

RUN groupadd -r $BOOTAPP_GROUP && \
      useradd -l -r -g $BOOTAPP_GROUP $BOOTAPP_USR && \
      chmod 555 /wrapper.sh

USER root
COPY maven/app.jar $BOOTAPP_PATH
RUN chmod 555 $BOOTAPP_PATH && \
            touch $BOOTAPP_PATH
USER $BOOTAPP_USR

ENTRYPOINT ["/wrapper.sh"]