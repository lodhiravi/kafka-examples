package myapps.clients.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import myapps.util.KafkaUtil;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class OrderFulfillmentConsumer {
    private final static String BOOTSTRAP_SERVERS = KafkaUtil.getBootstrapServer();
    private final static String TOPIC_NAME = "orders";
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        // Set up Kafka consumer properties
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "order-fulfillment-consumers");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Create Kafka consumer instances for different tasks
        Consumer<String, String> orderFulfillmentConsumer = new KafkaConsumer<>(props);

        // Subscribe each consumer to the orders topic
        orderFulfillmentConsumer.subscribe(Collections.singletonList(TOPIC_NAME));

        // Poll for orders and process them
        while (true) {
            ConsumerRecords<String, String> records = orderFulfillmentConsumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                Map<String, Object> order = OBJECT_MAPPER.readValue(record.value(), Map.class);
                fulFillOrder(order);
            }
        }
    }

    // Method to fulfill a given order
    private static void fulFillOrder(Map<String, Object> order) {
        System.out.println("Indexing order " + order.get("orderId") + " in Solr. Here is the detail: " + order);
        // Code to send notification goes here
    }
}
