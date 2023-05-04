package myapps.clients.example;

import java.util.Properties;

import myapps.util.KafkaUtil;
import org.apache.kafka.clients.producer.*;

public class KafkaProducerExample {
    private final static String BOOTSTRAP_SERVERS = KafkaUtil.getBootstrapServer();
    
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
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
