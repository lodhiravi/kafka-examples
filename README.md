# Kafka Examples

This project contains several example classes demonstrating the usage of Apache Kafka in Java to handle order events.

##Prerequisites
- Apache Kafka 3.3.1 or higher installed and running.
- Java 8 or higher installed.
- Maven 3.6.3 or higher installed.

##Bootstrap Server Config
Configure the bootstrap.server property in ```/kafka_home/config/kafka.properties``` for Kafka Server. 


##Build
To build the project, run the following command in the project root directory:

```mvn clean package```

This will compile the source code and package it into a JAR file.

##Managing Kafka Topic
The KafkaTopicManager example supports creation and deletion of topic. Run the following commands to create/delete topics.

### Create Topic
```mvn exec:java -Dexec.mainClass=myapps.topic.KafkaTopicManager -Dexec.args="create test0 1 1"```

Here first argument is for command, second argument is for topic name, third argument is for partition count and fourth argument is for replica factor.

### Delete Topic
```mvn exec:java -Dexec.mainClass=myapps.topic.KafkaTopicManager -Dexec.args="delete test0"```

##OrderProducer Example
To run the OrderProducer example, execute the following command:

```mvn exec:java -Dexec.mainClass=myapps.clients.order.OrderProducer```

This Producer example will read orders from data/Orders.json and produce/publish them to ```orders``` topic.

##OrderNotificationConsumer Example
To run the OrderNotificationConsumer example, execute the following command:

```mvn exec:java -Dexec.mainClass=myapps.clients.order.OrderNotificationConsumer```

This consumer example demonstrates how the notification service can subscribe to order events and send notifications to customers when their orders are processed.

##OrderIndexingConsumer Example
To run the OrderIndexingConsumer example, execute the following command:

```mvn exec:java -Dexec.mainClass=myapps.clients.order.OrderIndexingConsumer```

This consumer example demonstrates how the indexing service can subscribe to order events and update the search index with the latest order data.

##OrderFulfillmentConsumer Example
To run the OrderFulfillmentConsumer example, execute the following command:

```mvn exec:java -Dexec.mainClass=myapps.clients.order.OrderFulfillmentConsumer```

This consumer example demonstrates how the fulfillment service can subscribe to order events and fulfill customer orders.

##Conclusion
These examples provide a starting point to learn how kafka topics can be created/deleted. How the events/messages can be published to a kafka topic and how these events/messages can be read/consume from multiple consumers. Feel free to modify them to suit your needs and explore the full capabilities of Apache Kafka.