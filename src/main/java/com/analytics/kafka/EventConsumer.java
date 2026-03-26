package com.analytics.kafka;

import com.analytics.model.Event;
import com.analytics.service.EventService;
import com.analytics.service.MetricsService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumer {

    private final EventService eventService;
    private final MetricsService metricsService;

    public EventConsumer(EventService eventService, MetricsService metricsService) {
        this.eventService = eventService;
        this.metricsService = metricsService;
    }

    @KafkaListener(topics = "user-events", groupId = "analytics-group")
    public void consume(Event event) {

        System.out.println("Received event: " + event);

        // Save raw event
        eventService.saveEvent(event);

        // Process analytics
        metricsService.processEvent(event);
    }
}