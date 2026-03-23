package com.analytics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.analytics.model.Event;
import com.analytics.repository.EventRepository;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public static final Logger log = LoggerFactory.getLogger(EventService.class);

    public Event saveEvent(Event event) {
        log.info("Saving event: {}", event);
        return eventRepository.save(event);
    }
}
