FROM adoptopenjdk/openjdk8:latest as BUILD_IMAGE

ENV WORK_DIR=/usr/app/

WORKDIR $WORK_DIR

COPY gradlew $WORK_DIR
COPY build.gradle $WORK_DIR
COPY settings.gradle $WORK_DIR
COPY gradle $WORK_DIR/gradle

COPY coupon-common coupon-common
COPY coupon-service-api coupon-service-api
COPY coupon-login-api coupon-login-api

RUN ./gradlew -x test build || return 0

RUN ./gradlew bootjar

FROM adoptopenjdk/openjdk8:alpine

USER root

ENV WORK_DIR=/usr/app/
ENV DOCKERIZE_VERSION v0.6.1

WORKDIR $WORK_DIR
COPY --from=BUILD_IMAGE $WORK_DIR/coupon-service-api/build/libs/*.jar coupon-service-api.jar
COPY --from=BUILD_IMAGE $WORK_DIR/coupon-login-api/build/libs/*.jar coupon-login-api.jar

RUN apk add --no-cache openssl
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz
COPY ./script/app-entrypoint.sh app-entrypoint.sh
RUN chmod +x app-entrypoint.sh

ENTRYPOINT ["./app-entrypoint.sh"]