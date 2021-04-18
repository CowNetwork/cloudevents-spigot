package network.cow.cloudevents

import network.cow.cloudevents.kafka.CloudEventKafkaConsumer
import network.cow.cloudevents.kafka.CloudEventKafkaProducer
import network.cow.cloudevents.kafka.config.ConsumerConfig
import network.cow.cloudevents.kafka.config.ProducerConfig
import network.cow.grape.Grape
import org.bukkit.plugin.java.JavaPlugin
import java.net.URI
import java.util.UUID

/**
 * @author Benedikt Wüller
 */
class CloudEventsPlugin : JavaPlugin() {

    private lateinit var consumer: CloudEventKafkaConsumer

    override fun onEnable() {
        val brokers = this.config.getStringList("kafka.brokers")
        val topics = this.config.getStringList("kafka.topics")
        val source = this.config.getString("cloudevents.source") ?: "cow.minecraft.undefined"

        val groupIdPrefix = this.config.getString("kafka.group_id_prefix") ?: "cow.minecraft.undefined"
        val groupId = "$groupIdPrefix.${UUID.randomUUID()}"

        val producerConfig = ProducerConfig(brokers, URI.create(source))
        val consumerConfig = ConsumerConfig(brokers, topics, groupId)

        this.consumer = CloudEventKafkaConsumer(consumerConfig)
        this.consumer.start()

        Grape.getInstance().register(CloudEventsService::class.java, object : CloudEventsService {
            override val producer = CloudEventKafkaProducer(producerConfig)
            override val consumer = this@CloudEventsPlugin.consumer
        })
    }

    override fun onDisable() {
        this.consumer.stop()
    }

}
