package com.analytics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.analytics.model.Metrics;

public interface MetricsRepository extends JpaRepository<Metrics, Long> {
    List<Metrics> findByMetricType(String metricType);

    Metrics findByMetricTypeAndKeyNameAndTimestamp(
            String metricType,
            String keyName,
            Long timestamp);
}
