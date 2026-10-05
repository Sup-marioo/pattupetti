package com.pattupetti.pattu.controller;

import com.pattupetti.pattu.dto.TrackDto;
import com.pattupetti.pattu.service.MusicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/music")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping
    public String test() {
        return "Music Backend is running!";
    }

    @GetMapping("/search")
    public List<TrackDto> search(@RequestParam String q) {
        return musicService.search(q);
    }

    @GetMapping("/track/{id}")
    public TrackDto getTrack(@PathVariable String id) {
        return musicService.getTrack(id);
    }

    @GetMapping("/track/{id}/stream")
    public String stream(@PathVariable String id) {

        return musicService.getStreamUrl(id);
    }
}