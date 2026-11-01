package in.chandan.WatchTrack.catalog.controller;

import in.chandan.WatchTrack.catalog.dto.TitleSearchResponse;
import in.chandan.WatchTrack.catalog.entity.Episode;
import in.chandan.WatchTrack.catalog.service.EpisodeTrackingService;
import in.chandan.WatchTrack.catalog.service.TitleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/title")
public class TitleController {

    private TitleService titleService;
    private final EpisodeTrackingService episodeTrackingService;
    public TitleController(TitleService titleService, EpisodeTrackingService episodeTrackingService){
        this.titleService = titleService;
        this.episodeTrackingService = episodeTrackingService;
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<TitleSearchResponse> searchTitles(
            @PathVariable String query){
        return ResponseEntity.ok(titleService.searchTitles(query));
    }

    @GetMapping("/createNotification")
    public ResponseEntity<Integer> getEpisodes(){
        return ResponseEntity.ok(episodeTrackingService.createNotificationsForAiredEpisode());
    }
}
