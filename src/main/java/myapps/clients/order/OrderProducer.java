package myapps.clients.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class OrderProducer {
    private final static String TOPIC_NAME = "orders";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        // Set up Kafka producer properties
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create Kafka producer instance
        Producer<String, String> producer = new KafkaProducer<>(props);

        // Reads orders
        try {
            File jsonFile = new File("/home/ravi/ofbiz_dev/kafka/test-projects/streams.examples/data/Orders.json");
            List<Map<String, Object>> orders = OBJECT_MAPPER.readValue(jsonFile, new TypeReference<List<Map<String, Object>>>(){});

            for(Map<String, Object> order : orders) {
                // Convert the order to a JSON string
                String orderJson = OBJECT_MAPPER.writeValueAsString(order);

                // Create a ProducerRecord with the topic name and order JSON string
                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, orderJson);

                // Send the order to Kafka
                producer.send(record);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Close the producer instance
        producer.close();
    }
}

