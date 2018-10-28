# yelp-data-EDA
A demo shows how to explore data through Spark and Cassandra

## Motivation
When starting to find how people use Spark and Cassandra to explore yelp data set
https://www.yelp.com/dataset,
I found https://github.com/javrasya/yelp-data already includes a platform, including
writing data to Cassandra and explore data with jupyter notebook.

Two issues to note the data platform:
- If not enough memory, your cassandra container will exist immediately with code 137,
in my case it exists with 2 GB memory

- The password setting leads to login so I remove the password setting from my usage, as I run it locally, security is not a concern

## How to run
cd to yelp-data-platform and run `start-all.sh`
after jupyter notebook is available at `localhost:8888`, run
`start-data-processing.sh` to ingest data into cassandra
if you saw the ingestion task fails, that means your data does not fit the input format.
In my case I use the sample data provided, as in the data exploration phase,
it would be nice to save some time and see a quick result first.
Also, if you download the newest data set from yelp,
the ingestion fails, probably due to the change of the data format.
To fix it you would need to fix the code in the pipeline folder

## Demo Example
The motivation comes from challenging one conclusion of
https://github.com/backedwith/SQL---Yelp-Database-Analysis/blob/master/YelpDataCourseraPR.txt#L258
saying `Including number of fans in data set indicates that there is no correlation between review count and number of fans`, and my investigation shows the correlation is quite high based on Spearman correlation coefficient.
