package com.analytics.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analytics.dto.ApiResponse;
import com.analytics.dto.EventRequest;
import com.analytics.model.Event;
import com.analytics.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ApiResponse<Event> createEvent(@Valid @RequestBody EventRequest request) {
        Event event = new Event();
        event.setUserId(request.getUserId());
        event.setEventType(request.getEventType());
        event.setTimestamp(request.getTimeStamp());

        Event saved = eventService.saveEvent(event);

        return new ApiResponse<>("Event Created", saved);
    }
}
