package com.analytics.service;

import org.springframework.stereotype.Service;

import com.analytics.model.Event;
import com.analytics.repository.EventRepository;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }
}
