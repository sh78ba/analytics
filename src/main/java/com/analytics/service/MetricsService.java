package com.analytics.service;

import com.analytics.model.Event;
import com.analytics.model.Metrics;
import com.analytics.repository.MetricsRepository;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final MetricsRepository metricsRepository;

    public MetricsService(MetricsRepository metricsRepository) {
        this.metricsRepository = metricsRepository;
    }

    public void processEvent(Event event) {

        long minuteBucket = event.getTimestamp() / 60;

        Metrics existing = metricsRepository
                .findByMetricTypeAndKeyNameAndTimestamp(
                        "EVENTS_PER_MINUTE",
                        event.getEventType(),
                        minuteBucket);

        if (existing != null) {
            existing.setValue(existing.getValue() + 1);
            metricsRepository.save(existing);
        } else {
            Metrics metric = new Metrics(
                    "EVENTS_PER_MINUTE",
                    event.getEventType(),
                    1L,
                    minuteBucket);
            metricsRepository.save(metric);
        }
    }
}