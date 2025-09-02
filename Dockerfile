FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

RUN apk update && apk upgrade && \
    addgroup -g 1001 -S appuser && \
    adduser -u 1001 -S appuser -G appuser

COPY target/invoice.war invoice.war

RUN chown appuser:appuser invoice.war

USER appuser

EXPOSE 8080

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar invoice.war"]