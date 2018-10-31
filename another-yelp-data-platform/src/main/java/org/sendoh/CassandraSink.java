package org.sendoh;

import com.datastax.driver.core.Session;
import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.types.TypeConversionException;
import com.datastax.spark.connector.writer.WriteConf;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.Files.readAllBytes;

/**
 * Control the cassandra sink, including schema and the options to save
 */

public class CassandraSink implements DataSink {
    private static final String KEY_SPACE = "yelp";
    private static final Set<String> TABLES = new HashSet<>(Arrays.asList("tip", "business", "user", "review", "checkin"));

    private String schemaCQL;

    CassandraSink(SparkConf conf) throws URISyntaxException, IOException {
        schemaCQL = new String(readAllBytes(Paths
                .get(Objects.requireNonNull(CassandraSink.class.getClassLoader()
                        .getResource("schema.cql")).toURI())));
        setSchema(CassandraConnector.apply(conf));
    }

    // TODO: Log record when fail to save
    @Override
    public void write(Dataset<Row> row, String sourceFileName) {
        Optional<String> tableName = getTableName(sourceFileName);
        tableName.ifPresent(s -> row.write()
                .format("org.apache.spark.sql.cassandra")
                .mode(SaveMode.Append)
                .option("table", s)
                .option("keyspace", KEY_SPACE)
                .option("confirm.truncate", true)
                .save());
    }

    private void setSchema(CassandraConnector connector) {
        try (Session session = connector.openSession()) {
            // session can only run statement one by one, otherwise cql exception is thrown
            String[] split = schemaCQL.split(";");
            for (String statement : split) {
                session.execute(statement);
            }
        }
    }

    private Optional<String> getTableName(String fileName) {
        for (String table : TABLES) {
            if (fileName.contains(table)) {
                return Optional.of(table);
            }
        }
        return Optional.empty();
    }
}
