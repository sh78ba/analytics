package com.analytics.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.analytics.model.Event;
import com.analytics.service.EventService;

@Service
public class EventConsumer {
    private final EventService eventService;

    public EventConsumer(EventService eventService){
        this.eventService=eventService;
    }

    @KafkaListener(topics = "user-events",groupId = "analytics-group")
    public void consume(Event event){
        System.out.println("Received events" + event);
        eventService.saveEvent(event);
    }
}
