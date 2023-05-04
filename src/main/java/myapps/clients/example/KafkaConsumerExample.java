package myapps.clients.example;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import myapps.util.KafkaUtil;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaConsumerExample {
    private final static String BOOTSTRAP_SERVERS = KafkaUtil.getBootstrapServer();

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("group.id", "my-group");

        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        String topicName = "my-topic";

        consumer.subscribe(Collections.singletonList(topicName));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Received message: " + record.value());
            }
        }
    }
}
