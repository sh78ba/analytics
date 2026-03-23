package com.analytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.analytics.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
