FROM openjdk:8-jdk-alpine

RUN mkdir -p /apps
COPY ./entrypoint.sh /apps/entrypoint.sh
COPY ./target/*.jar /apps/app.jar
RUN chmod +x /apps/entrypoint.sh

CMD ["/apps/entrypoint.sh"]