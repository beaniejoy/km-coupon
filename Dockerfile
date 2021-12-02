FROM adoptopenjdk/openjdk8:latest as BUILD_IMAGE

ENV WORK_DIR=/usr/app/

WORKDIR $WORK_DIR

COPY gradlew $WORK_DIR
COPY build.gradle $WORK_DIR
COPY settings.gradle $WORK_DIR
COPY gradle $WORK_DIR/gradle

RUN ./gradlew -x test build || return 0

COPY src src

RUN ./gradlew bootjar

FROM adoptopenjdk/openjdk8:latest

ENV WORK_DIR=/usr/app/

WORKDIR $WORK_DIR

COPY --from=BUILD_IMAGE $WORK_DIR/build/libs/*.jar coupon-app.jar

ENTRYPOINT ["java", \
"-jar", \
"-Dspring.profiles.active=${PROFILE_OPTION}", \
"-Dspring.datasource.url=${SPRING_DATASOURCE_URL}", \
"coupon-app.jar"]