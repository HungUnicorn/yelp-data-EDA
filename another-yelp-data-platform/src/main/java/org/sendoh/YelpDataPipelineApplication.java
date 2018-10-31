package org.sendoh;

import com.datastax.driver.core.Session;
import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.writer.WriteConf;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import com.datastax.spark.connector.*;
import org.apache.spark.sql.cassandra.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;


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
                .config("spark.master", "local")
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
