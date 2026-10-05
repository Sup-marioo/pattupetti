package com.pattupetti.pattu.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import tools.jackson.databind.JsonNode;

@Component
public class AudiusClient {

    private final RestClient restClient;

    public AudiusClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.audius.co")
                .build();
    }

    public JsonNode searchTracks(String query) {

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/tracks/search")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .body(JsonNode.class);
    }

    public JsonNode getTrack(String id) {

        return restClient.get()
                .uri("/v1/tracks/{id}", id)
                .retrieve()
                .body(JsonNode.class);
    }

    public JsonNode getStreamUrl(String id) {

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/tracks/{id}/stream")
                        .queryParam("no_redirect", "true")
                        .build(id))
                .retrieve()
                .body(JsonNode.class);
    }
}