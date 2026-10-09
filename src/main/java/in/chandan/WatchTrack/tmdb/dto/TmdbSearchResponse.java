package in.chandan.WatchTrack.tmdb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TmdbSearchResponse {
    private int page;
    private int totalPages;
    private int totalResults;
    private List<TmdbSearchResult> results;

}
