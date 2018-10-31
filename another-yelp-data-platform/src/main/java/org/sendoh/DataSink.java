package org.sendoh;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface DataSink {
    void write(Dataset<Row> row, String table);
}
