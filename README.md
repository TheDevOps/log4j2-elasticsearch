# log4j2-elasticsearch overview

This is a parent project for log4j2 appender plugins capable of pushing logs in batches to Elasticsearch clusters.

Project consists of:
* `log4j-elasticsearch-core` - skeleton provider for conrete implementations
* `log4j-elasticsearch-jest` - [Jest HTTP Client](https://github.com/searchbox-io/Jest) compatible with Elasticsearch 2.x, 5.x and 6.x clusters
* `log4j-elasticsearch2-bulkprocessor` - [TCP client](https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.4/java-docs-bulk-processor.html) compatible with 2.x clusters
* `log4j-elasticsearch5-bulkprocessor` - [TCP client](https://www.elastic.co/guide/en/elasticsearch/client/java-api/5.6/java-docs-bulk-processor.html) compatible with 5.x and 6.x clusters

## Features

* Asynchronous log delivery
* Batch size and flush interval configuration
* Failover (redirect failed batch to alternative target)
* JSON message format ([user-provided](https://github.com/rfoltyns/log4j2-elasticsearch/blob/master/log4j2-elasticsearch-jest/src/test/java/org/appenders/log4j2/elasticsearch/jest/smoke/CustomMessageFactoryTest.java) or default)
* (since 1.1) Index rollover (hourly, daily, etc.)
* (1.1) Index template configuration
* (1.2) Basic Authentication (XPack Security and Shield support)
* (1.2) HTTPS support (XPack Security and Shield - visit submodules for compatibility matrix)

## Usage

1. Add this snippet to your `pom.xml` file:
```xml
<dependency>
    <groupId>org.appenders.log4j</groupId>
    <artifactId>log4j2-elasticsearch-jest</artifactId>
    <version>1.2.0</version>
</dependency>
```
(ensure that Log4j2 and Jackson FasterXML jars are added as well - see `Dependencies` section below)

2. Add this snippet to `log4j2.xml` configuration:
```xml
<Appenders>
    <Elasticsearch name="elasticsearchAsyncBatch">
        <IndexName indexName="log4j2" />
        <AsyncBatchDelivery>
            <JestHttp serverUris="http://localhost:9200" />
        </AsyncBatchDelivery>
    </Elasticsearch>
</Appenders>
```
or [configure programmatically](https://github.com/rfoltyns/log4j2-elasticsearch/blob/master/log4j2-elasticsearch-jest/src/test/java/org/appenders/log4j2/elasticsearch/jest/smoke/SmokeTest.java)

3. log.info("Hello, World!");

## Dependencies

Be aware that Jackson FasterXML jars that has to be provided by user for this library to work in default mode.
Please visit [mvnrepository](https://mvnrepository.com/artifact/org.appenders.log4j) for an overview of provided and compile dependencies

## Released to [Sonatype OSS repos](https://oss.sonatype.org/content/repositories/releases/org/appenders/log4j/)
Visit submodules' documentation or [mvnrepository](https://mvnrepository.com/artifact/org.appenders.log4j) for XML snippets.
