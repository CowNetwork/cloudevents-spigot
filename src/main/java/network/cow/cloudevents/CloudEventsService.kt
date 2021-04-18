package network.cow.cloudevents

import network.cow.cloudevents.kafka.CloudEventKafkaConsumer
import network.cow.cloudevents.kafka.CloudEventKafkaProducer
import network.cow.grape.Service

/**
 * @author Benedikt Wüller
 */
interface CloudEventsService : Service {
    val producer: CloudEventKafkaProducer
    val consumer: CloudEventKafkaConsumer
}
