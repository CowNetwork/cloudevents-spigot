# cloudevents-spigot
Cloud events provider for spigot using grape.

## Usage

```java
final CloudEventsService service = Grape.getInstance().get(CloudEventsService.class);

// Produce a cloud event.
service.getProducer().send(topic, protoMessage);

// Listen for cloud events of a specific type.
service.getConsumer().listen(protoTypeUrl, protoMessageClass, (message) -> {
    // callback
})
```
