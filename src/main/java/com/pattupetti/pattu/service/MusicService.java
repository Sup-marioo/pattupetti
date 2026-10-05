package com.pattupetti.pattu.service;

import tools.jackson.databind.JsonNode;
import com.pattupetti.pattu.client.AudiusClient;
import com.pattupetti.pattu.dto.TrackDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MusicService {

    private final AudiusClient audiusClient;

    public MusicService(AudiusClient audiusClient) {
        this.audiusClient = audiusClient;
    }

    public List<TrackDto> search(String query) {

        JsonNode response = audiusClient.searchTracks(query);

        List<TrackDto> tracks = new ArrayList<>();

        JsonNode data = response.get("data");

        if (data != null && data.isArray()) {

            for (JsonNode track : data) {

                String id = track.path("id").asText();
                String title = track.path("title").asText();
                String genre = track.path("genre").asText();
                int duration = track.path("duration").asInt();
                boolean streamable = track.path("is_streamable").asBoolean();

                String artist = "";

                JsonNode user = track.get("user");

                if (user != null) {
                    artist = user.path("name").asText();
                }

                String artworkUrl = "";

                JsonNode artwork = track.get("artwork");

                if (artwork != null) {
                    artworkUrl = artwork.path("150x150").asText();
                }

                TrackDto trackDto = new TrackDto(
                        id,
                        title,
                        artist,
                        genre,
                        duration,
                        artworkUrl,
                        streamable);

                tracks.add(trackDto);
            }
        }

        return tracks;
    }

    public TrackDto getTrack(String id) {

        JsonNode response = audiusClient.getTrack(id);

        JsonNode track = response.get("data");

        String trackId = track.path("id").asText();
        String title = track.path("title").asText();
        String genre = track.path("genre").asText();
        int duration = track.path("duration").asInt();
        boolean streamable = track.path("is_streamable").asBoolean();

        String artist = "";

        JsonNode user = track.get("user");

        if (user != null) {
            artist = user.path("name").asText();
        }

        String artworkUrl = "";

        JsonNode artwork = track.get("artwork");

        if (artwork != null) {
            artworkUrl = artwork.path("150x150").asText();
        }

        return new TrackDto(
                trackId,
                title,
                artist,
                genre,
                duration,
                artworkUrl,
                streamable);
    }

    public String getStreamUrl(String id) {

        JsonNode response = audiusClient.getStreamUrl(id);

        return response.path("data").asText();
    }
}