# Kafka Commands

## Create topic
```
 bin/kafka-topics.sh --create --topic test-producer --bootstrap-server <broker-id>:9092
```
## List all the topics
`
bin/kafka-topics.sh --list --bootstrap-server <broker-id>:9092
`

## Describe topic
`
bin/kafka-topics.sh --describe --topic test-producer --bootstrap-server <broker-id>:9092
`

## Produce message
`
bin/kafka-console-producer.sh --topic test-producer --bootstrap-server <broker-id>:9092
`

## Consume message
`
bin/kafka-console-consumer.sh --topic test-producer --from-beginning --bootstrap-server <broker-id>:9092
`

## Delete topic
`
bin/kafka-topics.sh --delete --topic test-producer --bootstrap-server <broker-id>:9092
`

## Consuming messages

`
bin/kafka-console-consumer.sh --topic nse-stock-may --bootstrap-server <broker-id>:9092
`

## Lab-2 Concurrent producer
1. Implement the `Runnable` interface
   
2. Load the Data file inside the `run` method
```java
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource(fileLocation).getFile());
    int counter = 0;
```
3. Read the line using `Scanner` class and send the message to Broker
```java
    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            producer.send(new ProducerRecord<>(topicName, null, line));
            counter++;
        }
        logger.info("Successfully sent " + counter + " messages from " + fileLocation);
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }
```
4. Create new Threads to send messages concurrently
```java
    Thread[] dispatchers = new Thread[AppConfig.dataFiles.length];
    logger.info("Starting Dispatcher threads...");
    for (int i = 0; i < AppConfig.dataFiles.length; i++) {
        dispatchers[i] = new Thread(new Executor(producer, AppConfig.topicName, AppConfig.dataFiles[i]));
        dispatchers[i].start();
    }
```
5. Stop the threads gracefully
```java
        try {
            for (Thread t : dispatchers) 
                t.join();
        } catch (InterruptedException e) {
            logger.error("Main Thread Interrupted");
        }
```
6. Shutdown the Producer instance gracefully
```java
 finally {
    producer.close();
    logger.info("Finished Dispatcher Demo");
 }
```

7. Verify the message produced by running the console consumer
```
bin/kafka-console-consumer.sh --topic nse-stock-may --bootstrap-server <broker-id>:9092
```

8. Delete the `nse-stock-may` topic
```
bin/kafka-topics.sh --delete --topic nse-stock-may --bootstrap-server <broker-id>:9092
```
