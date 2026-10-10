package in.chandan.WatchTrack.catalog.controller;

import in.chandan.WatchTrack.catalog.entity.Season;
import in.chandan.WatchTrack.catalog.repository.SeasonRepository;
import in.chandan.WatchTrack.catalog.service.SeasonService;
import in.chandan.WatchTrack.tmdb.dto.TmdbEpisodeDetails;
import in.chandan.WatchTrack.tmdb.dto.TmdbSeasonDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/title")
public class SeasonController {

    private SeasonService seasonService;
    public SeasonController(SeasonService seasonService){
        this.seasonService = seasonService;
    }
    @PostMapping("/{tvId}/season/{seasonNumber}")
    public ResponseEntity<Season> syncSeason(@PathVariable Long tvId ,
                                             @PathVariable Integer seasonNumber){
        return ResponseEntity.ok(seasonService.syncSeason(tvId,seasonNumber));
    }
}
