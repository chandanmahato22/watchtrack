package in.chandan.WatchTrack.tmdb.controller;

import in.chandan.WatchTrack.catalog.dto.TmdbMovieDetails;
import in.chandan.WatchTrack.catalog.dto.TmdbTvDetails;
import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.catalog.service.TitleService;
import in.chandan.WatchTrack.tmdb.service.TmdbClient;
import in.chandan.WatchTrack.tmdb.dto.TmdbSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TmdbController {

    private TmdbClient tmdbClient;

    @Autowired
    private TitleService titleService;

    public TmdbController(TmdbClient tmdbClient){
        this.tmdbClient = tmdbClient;
    }

    @GetMapping("/{name}")
    public ResponseEntity<TmdbSearchResponse> search(@PathVariable String name){
        return ResponseEntity.ok(tmdbClient.search(name));
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<TmdbMovieDetails> movieDetails(@PathVariable
                                               Long id){
            return ResponseEntity.ok(tmdbClient.getMovieDetails(id));
    }

    @PostMapping("/movie/{id}")
    public ResponseEntity<Title> saveMovie(@PathVariable Long id){
        Title resp = titleService.saveMovie(id);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/tv/{id}")
    public ResponseEntity<TmdbTvDetails> tvDetails(@PathVariable
                                                     Long id){
        return ResponseEntity.ok(tmdbClient.getTvDetails(id));
    }

    @PostMapping("/tv/{id}")
    public ResponseEntity<Title> saveTv(@PathVariable Long id){
        Title resp = titleService.saveTv(id);
        return ResponseEntity.ok(resp);
    }
}
