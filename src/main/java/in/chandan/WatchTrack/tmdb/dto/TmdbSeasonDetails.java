package in.chandan.WatchTrack.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class TmdbSeasonDetails {

    private Long id;
    private String name;
    private Integer seasonNumber;

    @JsonProperty("air_date")
    private String airDate;

    private List<TmdbEpisodeDetails> episodes;
}
