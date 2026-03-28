package com.analytics.service;

import com.analytics.model.Event;
import com.analytics.model.Metrics;
import com.analytics.repository.MetricsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final MetricsRepository metricsRepository;

    public MetricsService(MetricsRepository metricsRepository) {
        this.metricsRepository = metricsRepository;
    }

    @CacheEvict(value = { "eventsPerMinute", "summary" }, allEntries = true)
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

    @Cacheable(value = "eventsPerMinute", key = "'all'")
    public List<Metrics> getEventsPerMinute() {
        return metricsRepository.findByMetricType("EVENTS_PER_MINUTE");

    }

    @Cacheable(value = "summary", key = "'all'")
    public Map<String, Long> getSummary() {
        List<Metrics> metrics = metricsRepository.findByMetricType("EVENTS_PER_MINUTE");
        Map<String, Long> summary = new HashMap<>();

        for (Metrics m : metrics) {
            summary.put(m.getKeyName(), summary.getOrDefault(m.getKeyName(), 0L) + m.getValue());
        }
        return summary;
    }
}