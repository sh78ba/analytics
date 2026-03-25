package com.analytics.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.analytics.model.Event;

@Service
public class EventProducer {
    private final KafkaTemplate<String, Event> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvents(Event event) {
        kafkaTemplate.send("user-events", event.getUserId(), event);
    }
}
