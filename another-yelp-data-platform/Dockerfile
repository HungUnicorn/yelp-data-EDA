FROM bde2020/spark-java-template:2.3.2-hadoop2.7

ENV SPARK_APPLICATION_JAR_NAME yelp-data-pipeline-0.0.1-SNAPSHOT
ENV SPARK_APPLICATION_MAIN_CLASS org.sendoh.YelpDataPipelineApplication
ENV SPARK_MASTER_URL spark://spark-master:7077
ENV SPARK_APPLICATION_ARGS ""

ADD start.sh /

RUN chmod +x /start.sh

CMD ["/bin/bash", "/start.sh"]
