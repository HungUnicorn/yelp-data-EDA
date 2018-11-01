# yelp-data-EDA
A demo shows how to explore data through Spark and Cassandra

## another-yelp-data-platform
It contains a spark job to write files into cassandra, and jupyter notebook environment for querying data in cassandra. the spark docker environment setup refers to https://github.com/big-data-europe/docker-spark

### How to run
- run `docker-compose`
- `docker build` to build an image contains spark job
- `docker run --network=docker-spark_default --name my_container -e ENABLE_INIT_DAEMON=false my_image` this can optionally accept an environment variable `SPARK_APPLICATION_ARGS` pointing to the folder of yelp data files.
by default it uses the sample data taken by `head -n 1000`, which is the first 1000 lines of yelp data files

### Explaination
Writing to cassandra has to create schema first. This is controlled by the spark job. The schema.cql is put in main/resoures, and the 
CassandraSink controls the behaviour of creating schema and writes.

## Yelp-data-platform
### Motivation
When starting to find how people use Spark and Cassandra to explore yelp data set
https://www.yelp.com/dataset,
I found https://github.com/javrasya/yelp-data already includes a platform, including
writing data to Cassandra and exploring data with jupyter notebook.

Two issues to note the data platform:
- If not enough memory, your cassandra container will exit immediately with code 137,
in my case it exists with 2 GB memory

- The password setting leads to login so I remove the password setting from my usage, as I run it locally, security is not a concern

### Ignore login
You need to ignore asking password in this line
https://github.com/javrasya/yelp-data/blob/master/start-all.sh#L48

by
```    
docker run $_daemonize --name yelp_data_platform -p 8888:8888 --volume $TAR_FILE:/usr/lib/yelp_data/yelp_dataset.tar --network yelp_data_platform ahmetdal/yelp-data-platform start-notebook.sh --NotebookApp.token=''
```


### How to run
- cd to yelp-data-platform
- run `start-all.sh`, it will start jupyter environment and cassandra
- after jupyter notebook is available at `localhost:8888`, run
`start-data-processing.sh` to ingest data into cassandra. if you saw the ingestion task fails, that means your data does not fit the input format.


In my case I use the sample data provided, as in the data exploration phase,
it would be nice to save some time and see a quick result first.


Also, if you download the newest data set from yelp,
the ingestion fails, probably due to the change of the data format.
To fix it you would need to fix the code in the pipeline folder

## Demo Example
The motivation comes from challenging one conclusion of
https://github.com/backedwith/SQL---Yelp-Database-Analysis/blob/master/YelpDataCourseraPR.txt#L258
saying `Including number of fans in data set indicates that there is no correlation between review count and number of fans`, and my investigation shows the correlation is quite high based on Spearman correlation coefficient.

## another-yelp-data-platform
It writes data to cassandra using java
