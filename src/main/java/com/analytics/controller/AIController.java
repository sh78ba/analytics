package com.analytics.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analytics.service.AIService;
import com.analytics.service.MetricsService;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/insights")
public class AIController {
    private final MetricsService metricsService;
    private final AIService aiService;

    public AIController(MetricsService metricsService, AIService aiService) {
        this.metricsService = metricsService;
        this.aiService = aiService;
    }

    @GetMapping("")
    public String getInsights() {
        Map<String, Long> summary = metricsService.getSummary();
        return aiService.generateInsights(summary);
    }

}
