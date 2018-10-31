FROM ${docker.registry}/openjdk-8-slim:newrelic
MAINTAINER Deepak Kumar <deepak.kumar@fabhotels.com>

ENV ENV_NAME local
ENV NEW_RELIC_OPTS ""
ENV BOOTAPP_JAVA_OPTS -Xms256m -Xmx256m
ENV LOG_PATH "/var/log"
ENV BOOTAPP_USR="root" BOOTAPP_GROUP="root" BOOTAPP_PATH="/app.jar"
ENV SERVER_PORT 8000

EXPOSE $SERVER_PORT

COPY wrapper.sh /wrapper.sh

RUN chmod 555 /wrapper.sh


RUN if [ ${active.newrelic} = "TRUE" ] ; then NEW_RELIC_OPTS="-javaagent:/newrelic/newrelic.jar" ; NEW_RELIC_OPTS="" ; fi

RUN echo "spring.profile :::::: $spring.profiles.active"
RUN echo "Active branch :::::: ${active.newrelic}"
RUN echo "Docker registry :::::: $docker.registry"

USER root
COPY maven/app.jar $BOOTAPP_PATH
RUN chmod 555 $BOOTAPP_PATH && \
            touch $BOOTAPP_PATH
RUN mkdir $LOG_PATH            
RUN chmod 777 $LOG_PATH && touch $LOG_PATH 

USER $BOOTAPP_USR

RUN cat /newrelic/newrelic.yml_bk | sed -e 's/app_name:.*/app_name: docker_boot_ecs/' > /newrelic/newrelic.yml
 
ENTRYPOINT ["/wrapper.sh"]