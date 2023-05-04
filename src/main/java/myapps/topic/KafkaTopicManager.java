package myapps.topic;

import myapps.util.KafkaUtil;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaTopicManager {

    private static final int DEFAULT_NUM_PARTITIONS = 1;
    private static final short DEFAULT_REPLICATION_FACTOR = 1;
    private final static String BOOTSTRAP_SERVERS = KafkaUtil.getBootstrapServer();

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Set Kafka admin properties
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        // Create a Kafka admin client
        AdminClient adminClient = AdminClient.create(props);

        // Check command line arguments
        if (args.length < 2) {
            System.out.println("Usage: KafkaTopicManager create/delete topic_name [num_partitions] [replication_factor]");
            System.exit(1);
        }

        // Parse command line arguments
        String command = args[0];
        String topicName = args[1];
        int numPartitions = DEFAULT_NUM_PARTITIONS;
        short replicationFactor = DEFAULT_REPLICATION_FACTOR;

        if (args.length > 2) {
            numPartitions = Integer.parseInt(args[2]);
        }
        if (args.length > 3) {
            replicationFactor = Short.parseShort(args[3]);
        }

        if (command.equals("create")) {
            // Create a new Kafka topic
            NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
            adminClient.createTopics(Collections.singleton(newTopic)).all().get();
            System.out.println("Topic " + topicName + " created with " + numPartitions + " partitions and replication factor " + replicationFactor);
        } else if (command.equals("delete")) {
            // Delete a Kafka topic
            DeleteTopicsResult deleteResult = adminClient.deleteTopics(Collections.singleton(topicName));
            deleteResult.all().get();
            System.out.println("Topic " + topicName + " deleted");
        } else {
            System.out.println("Invalid command: " + command);
            System.exit(1);
        }

        // Close the admin client
        adminClient.close();
    }
}
