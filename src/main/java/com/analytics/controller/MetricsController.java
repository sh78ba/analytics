package com.analytics.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analytics.model.Metrics;
import com.analytics.service.MetricsService;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/metrics")
public class MetricsController {
    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/events-per-minute")
    public List<Metrics> getEventsPerMinute() {
        return metricsService.getEventsPerMinute();
    }

    @GetMapping("/summary")
    public Map<String, Long> getSummary() {
        return metricsService.getSummary();
    }

}
