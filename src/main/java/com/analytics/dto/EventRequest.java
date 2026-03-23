package com.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class EventRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String eventType;

    @NotNull
    private Long timeStamp;
}

