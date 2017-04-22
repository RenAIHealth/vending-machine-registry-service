FROM daocloud.io/java:8
RUN mkdir /app/
RUN mkdir /app/logs
RUN mkdir /app/target
RUN mkdir /app/buildtask
COPY ./target/machine-registry-0.0.1.jar /app/target
COPY ./buildtask/entrypoint.sh /app/
WORKDIR /app/
EXPOSE 9098 9098
CMD ["./entrypoint.sh"]