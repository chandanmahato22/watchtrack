package in.chandan.WatchTrack.catalog.controller;

import in.chandan.WatchTrack.catalog.dto.TitleSearchResponse;
import in.chandan.WatchTrack.catalog.service.TitleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/titles")
public class TitleController {

    private TitleService titleService;

    public TitleController(TitleService titleService){
        this.titleService = titleService;
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<TitleSearchResponse> searchTitles(
            @PathVariable String query){
        return ResponseEntity.ok(titleService.searchTitles(query));
    }
}
