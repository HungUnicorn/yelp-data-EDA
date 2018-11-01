package org.sendoh;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * Read json from files and write to cassandra
 */
public class YelpDataPipelineApplication {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String cassandraHost = "localhost";

        SparkSession ss = SparkSession
                .builder()
                .appName("yelp-data-pipeline")
                .config("spark.cassandra.connection.host", cassandraHost)
                .config("spark.master", System.getenv("SPARK_MASTER_URL"))
                .getOrCreate();

        DataSink sink = new CassandraSink(ss.sparkContext().getConf());

        FileSource source = new FileSource(args);

        source.getPaths().forEach(it -> {
            Dataset<Row> json = ss.read().json(it);
            sink.write(json, it);
        });
        ss.stop();
    }
}
