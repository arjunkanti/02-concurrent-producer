package io.classpath.kafka.examples.config;

public class AppConfig {
    public final static String applicationID = "Concurrent-Producer";
    public final static String topicName = "nse-stock-may";
    public final static String bootstrapServers = "<broker-1>:9092,<broker-2>:9092";
    public final static String[] dataFiles = {"data/NSE01MAY2021ATL.csv","data/NSE02MAY2021ATL.csv"};

}
