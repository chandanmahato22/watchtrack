package in.chandan.WatchTrack.catalog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.chandan.WatchTrack.enums.Provider;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TmdbMovieDetails {

    private Long id;
    private String title;
    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("release_date")
    private String releaseDate;

    private Integer runTime;

    @JsonProperty("vote_count")
    private Integer voteCount;

    @JsonProperty("vote_average")
    private Double voteAverage;

    private Provider provider = Provider.TMDB;
}
