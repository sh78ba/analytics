package com.analytics.service;

import java.util.Map;

import org.apache.kafka.common.protocol.types.Field.Str;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AIService {
    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateInsights(Map<String, Long> summary) {
        String prompt = """
                You are a data analyst.

                Analyze this user event data:
                %s

                Provide:
                1. Key insights
                2. Possible issues
                3. Suggestions
                """.formatted(summary.toString());

        String url = "https://api.openai.com/v1/chat/completions";

        String requestBody = String.format(
                "{\"model\":\"gpt-4o-mini\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}]}",
                prompt.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", " ").replace("\r", ""));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject(url, entity, String.class);

        return response;
    }
}
