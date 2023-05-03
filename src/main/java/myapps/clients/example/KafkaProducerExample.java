package myapps.clients.example;

import java.util.Properties;
import org.apache.kafka.clients.producer.*;

public class KafkaProducerExample {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        String topicName = "my-topic";
        String message = "Hello, Kafka!";

        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, message);

        producer.send(record);

        producer.close();
    }
}
